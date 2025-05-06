package com.daw2.fct_bbdd.auth.controllers;

import com.daw2.fct_bbdd.auth.jwt.JwtUtils;
import com.daw2.fct_bbdd.auth.models.user.User;
import com.daw2.fct_bbdd.auth.models.validCode.ValidCode;
import com.daw2.fct_bbdd.auth.payload.request.AuthCodeRequest;
import com.daw2.fct_bbdd.auth.payload.response.AuthCodeResponse;
import com.daw2.fct_bbdd.auth.repository.ValidCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/code")
public class ValidCodeController {

    @Autowired
    ValidCodeRepository repo;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public AuthCodeResponse checkCodeValidity(
            @RequestBody AuthCodeRequest request
    ) {
        ValidCode validCode = repo.findByCode(request.getCode()).orElse(null);
        AuthCodeResponse response;
        if ( validCode != null ) {
            User user = validCode.getUser();
            response = new AuthCodeResponse(true);
            response.setUser(user.getUsername());
            response.setToken(jwtUtils.generateJwtToken(user.getUsername()));
        } else {
            response = new AuthCodeResponse(false);
            response.setErrorMessage("Invalid code");
        }
        return response;
    }

}
