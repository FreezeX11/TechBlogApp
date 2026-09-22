package com.techBloggingApp.Backend.Payload.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
    private Long id;

    private String username;

    private String email;

    private List<String> roles;
}
