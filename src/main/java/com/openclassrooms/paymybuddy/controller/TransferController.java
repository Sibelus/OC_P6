package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class TransferController {

    @Autowired
    IUserService iUserService;

    @GetMapping("/transfer")
    public String welcomePage(Model model, String email) {
        Optional<User> newFriend = iUserService.findByEmail(email);

        model.addAttribute("newFriend", newFriend);
        return "transfer";
    }
}
