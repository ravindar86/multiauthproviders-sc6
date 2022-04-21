package com.example.multiauthproviderssc6.service;

import com.example.multiauthproviderssc6.entities.User;
import com.example.multiauthproviderssc6.repositories.UserRepository;
import com.example.multiauthproviderssc6.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> userDetails = userRepository.findUserByUsername(s);
        User user = userDetails.orElseThrow(()->new UsernameNotFoundException("User Details Not Found!"));
        return new SecurityUser(user);
    }
}
