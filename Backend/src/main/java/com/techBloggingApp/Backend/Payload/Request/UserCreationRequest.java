package com.techBloggingApp.Backend.Payload.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserCreationRequest {
    @NotBlank(message = "email shouldn't be null")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email invalide")
    private String email;

    @NotBlank(message = "username shouldn't be null")
    private String username;

    @Size(min = 8, max = 64, message = "Password must be between 2 and 50 characters")
    private String password;

    private Set<String> roles = new HashSet<>();
}
