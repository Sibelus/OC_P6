package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    IUserService iUserService;

    @GetMapping("/home")
    public String welcomePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Iterable<User> users = iUserService.getUsers();

        model.addAttribute("username", auth.getPrincipal());
        model.addAttribute("users", users);
        return "home";
    }
}
