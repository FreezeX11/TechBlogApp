package com.techBloggingApp.Backend.Payload.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class RefreshTokenResponse {
    private final String token;
    private final String refreshToken;
    private String tokenType = "Bearer";
}
