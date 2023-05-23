package com.haroonob.spring.security.springsecurity.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/admin")
@RestController
public class AdminController {
    @GetMapping("/welcome")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> admin(Authentication authentication) {
        return ResponseEntity.ok("Welcome Admin "+authentication.getPrincipal());
    }
}
