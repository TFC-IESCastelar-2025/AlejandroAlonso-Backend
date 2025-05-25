package com.daw2.fct_bbdd.auth.controllers;

import com.daw2.fct_bbdd.auth.jwt.JwtUtils;
import com.daw2.fct_bbdd.auth.models.role.Role;
import com.daw2.fct_bbdd.auth.models.role.RoleEnum;
import com.daw2.fct_bbdd.auth.models.user.User;
import com.daw2.fct_bbdd.auth.models.verificationToken.VerificationToken;
import com.daw2.fct_bbdd.auth.payload.request.LoginRequest;
import com.daw2.fct_bbdd.auth.payload.request.RegisterRequest;
import com.daw2.fct_bbdd.auth.payload.response.JwtResponse;
import com.daw2.fct_bbdd.auth.payload.response.MessageResponse;
import com.daw2.fct_bbdd.auth.repository.RoleRepository;
import com.daw2.fct_bbdd.auth.repository.UserRepository;
import com.daw2.fct_bbdd.auth.repository.VerificationTokenRepository;
import com.daw2.fct_bbdd.auth.services.EmailService;
import com.daw2.fct_bbdd.auth.services.UserService;
import com.daw2.fct_bbdd.auth.services.impl.UserDetailsImpl;
import com.daw2.fct_bbdd.common.utils.EnvironmentData;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    EnvironmentData env;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());

        if (optionalUser.isEmpty()) {
          return ResponseEntity
                  .badRequest()
                  .body(new MessageResponse("Error: Usuario no encontrado."));
        }

        User user = optionalUser.get();

        // Verificar si está habilitado
        if (!user.getEnabled()) {
          return ResponseEntity
                  .badRequest()
                  .body(new MessageResponse("Error: La cuenta no ha sido verificada. Revisa tu correo."));
        }
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
                    .body(new MessageResponse("El nombre de usuario " + signUpRequest.getUsername() + " ya existe."));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("El correo " + signUpRequest.getEmail() + " ya existe."));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, rol " + RoleEnum.ROLE_USER + " not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error, rol " + RoleEnum.ROLE_ADMIN + " not found"));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error, rol " + RoleEnum.ROLE_USER + " not found"));
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        // Generar y guardar token
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        verificationTokenRepository.save(verificationToken);

        // Enviar email
        emailService.sendVerificationEmail(user.getEmail(), token);

        return ResponseEntity.ok(new MessageResponse("Usuario registrado. Revisa tu correo para confirmar la cuenta."));
    }

   @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam String token) {
     Optional<VerificationToken> optToken = verificationTokenRepository.findByToken(token);

     if (optToken.isEmpty()) {
       return ResponseEntity.badRequest().body(new MessageResponse("Token inválido"));
     }

     VerificationToken verificationToken = optToken.get();
     if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
       return ResponseEntity.badRequest().body(new MessageResponse("Token expirado"));
     }

     User user = verificationToken.getUser();
     user.setEnabled(true);
     userRepository.save(user);

     return ResponseEntity.ok(new MessageResponse("Cuenta verificada con éxito."));
   }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("No existe una cuenta con ese correo."));
        }

        User user = userOpt.get();

        userService.deleteByTokenUserId(user.getId());

        String token = UUID.randomUUID().toString();
        VerificationToken resetToken = new VerificationToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        verificationTokenRepository.save(resetToken);

        String resetLink = env.getFRONT_ENVIRONMENT() +"/reset-password?token=" + token;
        emailService.sendPasswordResetEmail(user.getEmail(), resetLink);

        return ResponseEntity.ok(new MessageResponse("Se ha enviado un correo para restablecer la contraseña."));
    }

    @Transactional
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Optional<VerificationToken> optToken = verificationTokenRepository.findByToken(token);
        if (optToken.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Token inválido."));
        }

        VerificationToken verificationToken = optToken.get();

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(new MessageResponse("El token ha expirado."));
        }

        User user = verificationToken.getUser();
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);

        return ResponseEntity.ok(new MessageResponse("Contraseña actualizada con éxito."));
    }
}
