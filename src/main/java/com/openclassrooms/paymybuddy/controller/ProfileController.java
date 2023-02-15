package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/profile")
    public String profilePage(Model model) {
        User currentUser = iUserService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "profile";
    }
}
