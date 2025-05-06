package com.daw2.fct_bbdd.auth.controllers;

import com.daw2.fct_bbdd.auth.jwt.JwtUtils;
import com.daw2.fct_bbdd.auth.payload.response.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/validate")
public class UserDataValidityController {

  @Autowired
  JwtUtils jwtUtils;

  @GetMapping("/token")
  public ValidationResponse validateToken(
    @RequestParam("jwt") String jwt
  ) {
    ValidationResponse response = new ValidationResponse(jwtUtils.validateJwtToken(jwt));
    if (response.getAccepted()) {
      response.setUser(jwtUtils.getUserNameFromJwtToken(jwt));
      response.setToken(jwt);
    } else {
      response.setErrorMessage("Invalid token.");
    }
    return response;
  }

  @GetMapping("/all")
  public ValidationResponse allAccess() {
    return new ValidationResponse(true);
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ValidationResponse userAccess() {
    return new ValidationResponse(true);
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public ValidationResponse adminAccess() {
    return new ValidationResponse(true);
  }

}
