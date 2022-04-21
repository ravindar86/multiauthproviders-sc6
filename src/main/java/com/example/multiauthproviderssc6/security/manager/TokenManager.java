package com.example.multiauthproviderssc6.security.manager;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TokenManager {

    private Set<String> tokens = new HashSet<>();

    public boolean contains(String token){
        return tokens.contains(token);
    }

    public void addToken(String token) {
        this.tokens.add(token);
    }
}
