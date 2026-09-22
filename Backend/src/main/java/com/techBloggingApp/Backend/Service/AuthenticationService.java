package com.techBloggingApp.Backend.Service;

import com.techBloggingApp.Backend.Entity.RefreshToken;
import com.techBloggingApp.Backend.Entity.Role;
import com.techBloggingApp.Backend.Entity.User;
import com.techBloggingApp.Backend.Enum.ERole;
import com.techBloggingApp.Backend.Exception.BusinessException;
import com.techBloggingApp.Backend.Exception.DisabledAccountException;
import com.techBloggingApp.Backend.Exception.ResourceNotFoundException;
import com.techBloggingApp.Backend.Mapper.UserMapper;
import com.techBloggingApp.Backend.Payload.Request.LoginRequest;
import com.techBloggingApp.Backend.Payload.Request.RefreshTokenRequest;
import com.techBloggingApp.Backend.Payload.Request.UserCreationRequest;
import com.techBloggingApp.Backend.Payload.Response.RefreshTokenResponse;
import com.techBloggingApp.Backend.Payload.Response.UserResponse;
import com.techBloggingApp.Backend.Repository.RoleRepository;
import com.techBloggingApp.Backend.Repository.UserRepository;
import com.techBloggingApp.Backend.Config.Security.CustomUserDetails;
import com.techBloggingApp.Backend.Config.Security.Jwt.JwtUtils;
import com.techBloggingApp.Backend.ServiceInterface.IAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public Map<String, Object>  login(LoginRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();

        log.debug("Login attempt initiated for the user {}.", loginRequest.getUsername());

        try {
            Authentication authenticationRequest = UsernamePasswordAuthenticationToken
                    .unauthenticated(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    );

            Authentication authentication = authenticationManager.authenticate(authenticationRequest);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

            ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

            UserResponse userResponse = new UserResponse(
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles
            );

            response.put("jwtCookie", jwtCookie);
            response.put("jwtRefreshCookie", jwtRefreshCookie);
            response.put("userResponse", userResponse);

            log.info("User login successfully: {} (ID {}).", loginRequest.getUsername(), userDetails.getId());

            return response;
        } catch (DisabledException e) {
            log.warn("Connection failed: The user account {} is disabled.", loginRequest.getUsername());
            throw new DisabledAccountException("Account is disabled");

        } catch (BadCredentialsException e) {
            log.warn("Connection failed: Invalid credential for user {}", loginRequest.getUsername());
            throw new BadCredentialsException("Invalid credentials");

        } catch (Exception e) {
            log.error("Unexpected technical error while attempting to log in the user {}.", loginRequest.getUsername());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void register(UserCreationRequest userCreationRequest) {
        log.debug("Account creation attempt with the email account {} and username {}.",
                userCreationRequest.getEmail(), userCreationRequest.getUsername());

        if (userRepository.existsByEmail(userCreationRequest.getEmail())) {
            log.warn("Registration failed : the email {} is already used.", userCreationRequest.getEmail());
            throw new BusinessException(userCreationRequest.getEmail());
        }

        if (userRepository.existsByUsername(userCreationRequest.getUsername())) {
            log.warn("Registration failed : the username {} is already used.", userCreationRequest.getUsername());
            throw new BusinessException(userCreationRequest.getUsername());
        }

        User user = userMapper.toUser(userCreationRequest);

        Set<String> strRoles = userCreationRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles.isEmpty()) {
            Role role = roleRepository.findByRole(ERole.ROLE_USER)
                    .orElseThrow(() -> {
                        log.error("Role ROLE_USER doesn't exist in database !");
                        return new ResourceNotFoundException("Role not found .");
                    });

            roles.add(role);
        } else {
            strRoles.forEach(strRole -> {
               Role role = roleRepository.findByRole(ERole.valueOf(strRole))
                       .orElseThrow(() -> {
                           log.error("Role {} doesn't exist in database !", strRole);
                           return new ResourceNotFoundException("Role introuvable.");
                       });

               roles.add(role);
            });
        }

        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        log.info("User registered successfully. Username {}, ID {}", userCreationRequest.getUsername(), savedUser.getId());
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
       RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken());
       refreshToken =  refreshTokenService.verifyExpiration(refreshToken);

       User user = refreshToken.getUser();
       String token = jwtUtils.generateTokenFromUsername(user.getUsername());

        return new RefreshTokenResponse(token, refreshToken.getToken());
    }

    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            log.warn("Anonymous log out attempt or user not authenticated.");
            return;
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("Log out attempt for user : {} (ID {}).", userDetails.getUsername(), userDetails.getId());

        try {
            refreshTokenService.deleteByUserId(userDetails.getId());

            log.info("Log out successfully. The refresh of the user {} has been deleted", userDetails.getUsername());

        } catch (Exception e) {
            log.error("An error occur during the refresh token suppression of the user {}", userDetails.getUsername(), e);
            throw e;
        }


    }
}
