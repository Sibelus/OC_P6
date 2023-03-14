package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private IUserService iUserService;
    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/home")
    public String welcomePage(Model model) {
        User currentUser = iUserService.getCurrentUser();
        String welcomMessage = "Welcom " + currentUser.getFirstname() + " " + currentUser.getLastname();
        logger.debug("{} {} connect the application with his id & password", currentUser.getFirstname(), currentUser.getLastname());

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("welcomMessage", welcomMessage);
        return "home";
    }
}
