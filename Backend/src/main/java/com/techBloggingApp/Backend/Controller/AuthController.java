package com.techBloggingApp.Backend.Controller;

import com.techBloggingApp.Backend.Payload.Request.LoginRequest;
import com.techBloggingApp.Backend.Payload.Request.RefreshTokenRequest;
import com.techBloggingApp.Backend.Payload.Request.UserCreationRequest;
import com.techBloggingApp.Backend.Payload.Response.ApiResponse;
import com.techBloggingApp.Backend.Payload.Response.RefreshTokenResponse;
import com.techBloggingApp.Backend.Payload.Response.UserResponse;
import com.techBloggingApp.Backend.Config.Security.Jwt.JwtUtils;
import com.techBloggingApp.Backend.Service.AuthenticationService;
import com.techBloggingApp.Backend.Service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(@Valid LoginRequest loginRequest) {
        Map<String, Object> response = authenticationService.login(loginRequest);

        UserResponse userResponse = (UserResponse) response.get("userResponse");

        ApiResponse<UserResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                userResponse
        );

        ResponseCookie jwtCookie = (ResponseCookie) response.get("jwtCookie");
        ResponseCookie jwtRefreshCookie = (ResponseCookie) response.get("jwtRefreshCookie");

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(apiResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserCreationRequest userCreationRequest) {
        authenticationService.register(userCreationRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        ApiResponse<RefreshTokenResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                authenticationService.refreshToken(refreshTokenRequest)
        );

        return new ResponseEntity<>(
                apiResponse,
                HttpStatus.OK
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authenticationService.logout();
        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(null);
    }
}
