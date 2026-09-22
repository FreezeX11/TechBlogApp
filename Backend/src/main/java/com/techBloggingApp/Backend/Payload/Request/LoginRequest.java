package com.techBloggingApp.Backend.Payload.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Email can't be null")
    @Email
    private String username;

    @NotBlank(message = "Password can't be null")
    private String password;
}
