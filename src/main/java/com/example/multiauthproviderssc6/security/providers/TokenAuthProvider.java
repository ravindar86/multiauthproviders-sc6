package com.example.multiauthproviderssc6.security.providers;

import com.example.multiauthproviderssc6.security.authentication.TokenAuthentication;
import com.example.multiauthproviderssc6.security.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthProvider implements AuthenticationProvider {

    @Autowired
    private TokenManager tokenManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();
        boolean tokenExist = tokenManager.contains(token);

        if(tokenExist)
            return new TokenAuthentication(token, null, null);

        throw new BadCredentialsException("Invalid Token!");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TokenAuthentication.class.equals(aClass);
    }
}
