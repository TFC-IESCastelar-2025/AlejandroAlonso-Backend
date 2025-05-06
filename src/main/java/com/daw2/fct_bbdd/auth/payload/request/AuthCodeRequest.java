package com.daw2.fct_bbdd.auth.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthCodeRequest {
    @NotBlank
    private String code;
}
