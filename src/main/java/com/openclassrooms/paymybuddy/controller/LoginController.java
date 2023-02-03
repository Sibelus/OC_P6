package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private IUserService iUserService;


    @GetMapping("/")
    public String homePage(Model model) {
        return "login";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/createAccount")
    public String createAccountForm(Model model) {
        model.addAttribute("user", new User());
        return "createAccount";
    }

    @PostMapping("/createAccount")
    public String createAccountSubmit(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        iUserService.addUser(user);
        return "login";
    }


}
