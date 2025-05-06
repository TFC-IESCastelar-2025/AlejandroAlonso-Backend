package com.daw2.fct_bbdd.auth.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class AuthCodeResponse {

    private Boolean accepted;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String user;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;



    public AuthCodeResponse(Boolean accepted) {
        this.accepted = accepted;
    }

}
