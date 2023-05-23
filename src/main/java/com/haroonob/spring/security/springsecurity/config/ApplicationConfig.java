package com.haroonob.spring.security.springsecurity.config;

import com.haroonob.spring.security.springsecurity.services.SecurityUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class ApplicationConfig {
    @Bean
    public DaoAuthenticationProvider authenticationProvider(SecurityUserDetailsService securityUserDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(securityUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        int strength = 10; // The log rounds of the BCrypt hashing function
        SecureRandom secureRandom = new SecureRandom();
        byte[] seed = secureRandom.generateSeed(12); // Generate a random seed
        secureRandom.setSeed(seed); // Set the seed for the SecureRandom instance
        return new BCryptPasswordEncoder(strength, secureRandom);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
