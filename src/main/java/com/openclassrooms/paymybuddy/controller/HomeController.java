package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/home")
    public String welcomePage(Model model) {
        Iterable<User> users = iUserService.getUsers();
        User currentUser = iUserService.getCurrentUser();
        String welcomMessage = "Welcom " + currentUser.getFirstname() + " " + currentUser.getLastname();

        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("welcomMessage", welcomMessage);
        return "home";
    }
}
