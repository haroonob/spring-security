package com.haroonob.spring.security.springsecurity;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class SignatureKey {

    public static void main(String[] args) {
        // Generate the HS256 signature key
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Convert the key to Base64 for storage or transmission
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());

        System.out.println("HS256 Signature Key: " + encodedKey);
    }
}
