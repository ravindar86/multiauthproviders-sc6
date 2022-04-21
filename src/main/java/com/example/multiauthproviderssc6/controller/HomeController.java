package com.example.multiauthproviderssc6.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/hello")
    public String sayHello(Authentication authentication){

        // Way 1 - getting the authentication details using the param
        System.out.println("Authentication via param : "+authentication.getName());

        // Way 2 - getting the authentication details from the context
        Authentication auhenticationCtx = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication via getAuthentication : "+auhenticationCtx.getName());

        return "Multiple Authentication Providers Implemented Successfully! "
                        +"; param= "+authentication.getName() + " ; name = "+auhenticationCtx.getName();
    }
}
