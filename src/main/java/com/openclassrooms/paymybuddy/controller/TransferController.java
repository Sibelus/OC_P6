package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TransferController {

    @Autowired
    IUserService iUserService;

    @GetMapping("/transfer")
    public String welcomePage(Model model, String email) {
        Optional<User> newFriend = iUserService.findByEmail(email);
        List<User> friendList = new ArrayList<>();

        model.addAttribute("newFriend", newFriend);
        model.addAttribute("friendList", friendList);
        return "transfer";
    }

    @PostMapping("/transfer_newFriend")
    public String createAccountSubmit(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        iUserService.addUser(user);
        return "transfer";
    }
}
