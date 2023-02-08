package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IBankAccountService;
import com.openclassrooms.paymybuddy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddBankAccountController {

    @Autowired
    IBankAccountService iBankAccountService;
    @Autowired
    IUserService iUserService;

    @GetMapping("/addBankAccount")
    public String createAccountForm(Model model) {
        model.addAttribute("bankAccount", new BankAccount());
        return "addBankAccount";
    }

    @PostMapping("/addBankAccount")
    public String createAccountSubmit(@ModelAttribute BankAccount bankAccount, Model model) {
        model.addAttribute("bankAccount", bankAccount);
        iBankAccountService.addBankAccount(bankAccount);
        User currentUser = iUserService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "profile";
    }
}