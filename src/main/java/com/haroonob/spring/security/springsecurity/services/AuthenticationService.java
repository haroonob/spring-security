package com.haroonob.spring.security.springsecurity.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroonob.spring.security.springsecurity.config.JwtTokenUtilService;
import com.haroonob.spring.security.springsecurity.dto.AuthenticationRequest;
import com.haroonob.spring.security.springsecurity.dto.AuthenticationResponse;
import com.haroonob.spring.security.springsecurity.dto.RegisterRequest;
import com.haroonob.spring.security.springsecurity.model.User;
import com.haroonob.spring.security.springsecurity.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
 
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenUtilService jwtUtilService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .username(request.getUserName())
        .password(passwordEncoder.encode(request.getPassword()))
        .roles(request.getRoles())
        .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtUtilService.generateToken(user);
    var refreshToken = jwtUtilService.generateRefreshToken(user);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );
    var user = repository.findByUsername(request.getUsername())
        .orElseThrow();
    var jwtToken = jwtUtilService.generateToken(user);
    var refreshToken = jwtUtilService.generateRefreshToken(user);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }
  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtUtilService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findById(userEmail)
              .orElseThrow();
      if (jwtUtilService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtUtilService.generateToken(user);

        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
