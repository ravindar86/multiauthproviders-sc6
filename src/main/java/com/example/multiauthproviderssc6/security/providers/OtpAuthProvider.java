package com.example.multiauthproviderssc6.security.providers;

import com.example.multiauthproviderssc6.entities.Otp;
import com.example.multiauthproviderssc6.repositories.OtpRepository;
import com.example.multiauthproviderssc6.security.authentication.UsernameOtpAuthentication;
import com.example.multiauthproviderssc6.service.JpaOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OtpAuthProvider implements AuthenticationProvider {

    @Autowired
  //  private OtpRepository otpRepository;
    private JpaOtpService otpService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String otp =(String)authentication.getCredentials();

     //   Optional<Otp> otpObj = otpRepository.findOtpByUsername(username);
        Optional<Otp> otpObj = otpService.getOtpByUsername(username);
        if(otpObj.isPresent()){

            List<GrantedAuthority> listAuthorities = new ArrayList<>();
            listAuthorities.add(new SimpleGrantedAuthority("read"));

            return new UsernameOtpAuthentication(username, otp, listAuthorities);
        }

       throw new BadCredentialsException("Otp is incorrect");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernameOtpAuthentication.class.equals(aClass);
    }
}
