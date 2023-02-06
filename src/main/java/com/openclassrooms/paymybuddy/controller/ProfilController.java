package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfilController {

    @Autowired
    IUserService iUserService;

    @GetMapping("/profile")
    public String loginPage(Model model) {
        String currentUserEmail = iUserService.getCurrentUserEmail();
        User currentUser = iUserService.findByEmail(currentUserEmail).get();
        model.addAttribute("currentUser", currentUser);
        return "profile";
    }
}