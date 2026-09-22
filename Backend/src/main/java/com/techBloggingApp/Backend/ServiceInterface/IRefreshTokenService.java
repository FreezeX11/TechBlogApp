package com.techBloggingApp.Backend.ServiceInterface;

import com.techBloggingApp.Backend.Entity.RefreshToken;

public interface IRefreshTokenService {
    RefreshToken findByToken(String token);
    RefreshToken createRefreshToken(Long userId);
    RefreshToken verifyExpiration(RefreshToken token);
    void deleteByUserId(Long userId);
}
