package com.daw2.fct_bbdd.auth.controllers;

import com.daw2.fct_bbdd.auth.jwt.JwtUtils;
import com.daw2.fct_bbdd.auth.models.role.Role;
import com.daw2.fct_bbdd.auth.models.role.RoleEnum;
import com.daw2.fct_bbdd.auth.models.user.User;
import com.daw2.fct_bbdd.auth.payload.request.LoginRequest;
import com.daw2.fct_bbdd.auth.payload.request.RegisterRequest;
import com.daw2.fct_bbdd.auth.payload.response.JwtResponse;
import com.daw2.fct_bbdd.auth.payload.response.MessageResponse;
import com.daw2.fct_bbdd.auth.repository.RoleRepository;
import com.daw2.fct_bbdd.auth.repository.UserRepository;
import com.daw2.fct_bbdd.auth.services.impl.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Name "+signUpRequest.getUsername()+" already registered."));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email "+signUpRequest.getEmail()+" already registered."));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error, rol "+RoleEnum.ROLE_USER+" not found"));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
          if (role.equals("admin")) {
              Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                      .orElseThrow(() -> new RuntimeException("Error, rol "+RoleEnum.ROLE_ADMIN+" not found"));
              roles.add(adminRole);
          } else {
              Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                      .orElseThrow(() -> new RuntimeException("Error, rol "+RoleEnum.ROLE_USER+" not found"));
              roles.add(userRole);
          }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User "+user.getUsername()+" registered correctly."));
  }
}
