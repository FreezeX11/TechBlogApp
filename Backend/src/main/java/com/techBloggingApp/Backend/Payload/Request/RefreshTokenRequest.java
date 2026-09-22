package com.techBloggingApp.Backend.Payload.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequest {
    @NotBlank(message = "Refresh token should not be null")
    private String refreshToken;
}
