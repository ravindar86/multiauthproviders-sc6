package com.example.multiauthproviderssc6.security.filter;

import com.example.multiauthproviderssc6.entities.Otp;
import com.example.multiauthproviderssc6.security.authentication.UsernameOtpAuthentication;
import com.example.multiauthproviderssc6.security.authentication.UsernamePasswordAuthentication;
import com.example.multiauthproviderssc6.security.manager.TokenManager;
import com.example.multiauthproviderssc6.service.JpaOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

//@Component
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JpaOtpService otpService;

    @Autowired
    private TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String username = httpServletRequest.getHeader("username");
        String password = httpServletRequest.getHeader("password");
        String otp = httpServletRequest.getHeader("otp");

        if(otp==null){
            // Step 1 : username & password validation
            Authentication usernameAuth = new UsernamePasswordAuthentication(username,password);
            authenticationManager.authenticate(usernameAuth);

            String otpCode = String.valueOf(new Random().nextInt(999)+1000);

            Otp otpEntity = new Otp();
            otpEntity.setUsername(username);
            otpEntity.setOtp(otpCode);

            otpService.removeOtp();
            otpService.saveOtp(otpEntity);
         //   SecurityContextHolder.getContext().setAuthentication(usernameAuth);
        }else{
            // Step 2:  username & otp validation
            Authentication otpAuth = new UsernameOtpAuthentication(username, otp);
            authenticationManager.authenticate(otpAuth);

            String token = UUID.randomUUID().toString();
            tokenManager.addToken(token);

            httpServletResponse.setHeader("Authorization", token);
           // SecurityContextHolder.getContext().setAuthentication(otpAuth);
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return !request.getServletPath().equals("/login");
    }
}
