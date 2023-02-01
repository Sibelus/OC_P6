package com.openclassrooms.paymybuddy.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/*")
    @RolesAllowed("USER")
    public String getUser(){
        return "Welcome user ^^";
    }
}
