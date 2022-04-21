package com.example.multiauthproviderssc6.security.filter;

import com.example.multiauthproviderssc6.security.authentication.TokenAuthentication;
import com.example.multiauthproviderssc6.security.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class TokenAuthFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String token = (String) httpServletRequest.getHeader("Authorization");

        Authentication tokenAuthentication = new TokenAuthentication(token, null);
        tokenAuthentication = authenticationManager.authenticate(tokenAuthentication);

        SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().contains("/login");
    }
}
