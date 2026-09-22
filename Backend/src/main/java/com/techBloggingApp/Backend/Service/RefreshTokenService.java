package com.techBloggingApp.Backend.Service;

import com.techBloggingApp.Backend.Entity.RefreshToken;
import com.techBloggingApp.Backend.Entity.User;
import com.techBloggingApp.Backend.Exception.RefreshTokenException;
import com.techBloggingApp.Backend.Exception.ResourceNotFoundException;
import com.techBloggingApp.Backend.Exception.UserNotFoundException;
import com.techBloggingApp.Backend.Repository.RefreshTokenRepository;
import com.techBloggingApp.Backend.Repository.UserRepository;
import com.techBloggingApp.Backend.ServiceInterface.IRefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService implements IRefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    @Value("${techblog.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("This token doesn't exist"));
    }

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User " + userId + "not found"));

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(existingUser);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException(token.getToken() + "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Override
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUser_Id(userId);
    }
}
