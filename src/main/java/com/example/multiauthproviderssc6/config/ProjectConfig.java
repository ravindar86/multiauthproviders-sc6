package com.example.multiauthproviderssc6.config;

import com.example.multiauthproviderssc6.security.filter.TokenAuthFilter;
import com.example.multiauthproviderssc6.security.filter.UsernamePasswordAuthFilter;
import com.example.multiauthproviderssc6.security.providers.OtpAuthProvider;
import com.example.multiauthproviderssc6.security.providers.TokenAuthProvider;
import com.example.multiauthproviderssc6.security.providers.UsernamePasswordAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsernamePasswordAuthProvider usernamePasswordAuthProvider;

    @Autowired
    private OtpAuthProvider otpAuthProvider;

    @Autowired
    private TokenAuthProvider tokenAuthProvider;

   // @Autowired
   // private UsernamePasswordAuthFilter usernamePasswordAuthFilter;

   // @Autowired
   // private TokenAuthFilter tokenAuthFilter;

    @Bean
    public UsernamePasswordAuthFilter usernamePasswordAuthFilter() {
        return new UsernamePasswordAuthFilter();
    }

    @Bean
    public TokenAuthFilter tokenAuthFilter(){
        return new TokenAuthFilter();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.authenticationProvider(usernamePasswordAuthProvider)
               .authenticationProvider(otpAuthProvider)
                .authenticationProvider(tokenAuthProvider);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
       http.addFilterAt(usernamePasswordAuthFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(tokenAuthFilter(), BasicAuthenticationFilter.class);
    }
}
