package com.daw2.fct_bbdd.auth.jwt;

import com.daw2.fct_bbdd.auth.services.impl.UserDetailsImpl;
import com.daw2.fct_bbdd.common.utils.EnvironmentData;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

  @Autowired
  EnvironmentData env;

  private String jwtSecret ;
  private int jwtExpirationMs ;

  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @PostConstruct
  private void getEnvironment() {
    jwtSecret = env.getJWT_SECRET_TOKEN();
    jwtExpirationMs = Integer.parseInt(env.getJWT_EXP_TIME());
  }

  public String generateJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String generateJwtToken(String username) {
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact();
  }
  
  private Key key() {
    //return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    return Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(key())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Token JWT no válido: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("El token JWT ha expirado: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("El token JWT no es compatible: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT afirma que la cadena está vacía: {}", e.getMessage());
    }

    return false;
  }
}
