package com.techBloggingApp.Backend.ServiceInterface;

import com.techBloggingApp.Backend.Payload.Request.LoginRequest;
import com.techBloggingApp.Backend.Payload.Request.RefreshTokenRequest;
import com.techBloggingApp.Backend.Payload.Request.UserCreationRequest;
import com.techBloggingApp.Backend.Payload.Response.RefreshTokenResponse;
import com.techBloggingApp.Backend.Payload.Response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface IAuthenticationService {
    Map<String, Object> login(LoginRequest loginRequest);
    void register(UserCreationRequest userCreationRequest);
    RefreshTokenResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest);
    void logout();
}
