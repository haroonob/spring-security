package com.haroonob.spring.security.springsecurity.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/v1/")
@RestController
public class WelomeController {
    @GetMapping("/welcome")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> greeting(Authentication authentication) {
        return ResponseEntity.ok("Welcome User "+authentication.getPrincipal());
    }


}