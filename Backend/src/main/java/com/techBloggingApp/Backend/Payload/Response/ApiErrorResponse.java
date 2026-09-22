package com.techBloggingApp.Backend.Payload.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
    private String title;

    private int status;

    private String detail;

    private String path;

    private Instant timestamp;
}
