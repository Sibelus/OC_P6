package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IBankTransationService;
import com.openclassrooms.paymybuddy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BankTransactionController {

    @Autowired
    IUserService iUserService;
    @Autowired
    IBankTransationService iBankTransationService;

    @GetMapping("/bankTransaction")
    public String bankTransactionPage(Model model){
        BankTransaction bankTransaction = new BankTransaction();
        User currentUser = iUserService.getCurrentUser();
        List<BankAccount> bankAccounts = currentUser.getBankAccountList();
        model.addAttribute("bankTransaction", bankTransaction);
        model.addAttribute("bankAccounts", bankAccounts);
        return "bankTransaction";
    }

    @PostMapping("/bankTransaction_bankToUser")
    public String bankToUserSubmit(@ModelAttribute BankTransaction bankTransaction, Model model){
        User currentUser = iUserService.getCurrentUser();
        List<BankAccount> bankAccounts = currentUser.getBankAccountList();
        bankTransaction.setUser(currentUser);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("bankAccounts", bankAccounts);
        model.addAttribute("bankTransaction", bankTransaction);
        iBankTransationService.bankToUser(bankTransaction);
        return "profile";
    }

    @PostMapping("/bankTransaction_userToBank")
    public String userToBankSubmit(@ModelAttribute BankTransaction bankTransaction, Model model){
        User currentUser = iUserService.getCurrentUser();
        List<BankAccount> bankAccounts = currentUser.getBankAccountList();
        bankTransaction.setUser(currentUser);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("bankAccounts", bankAccounts);
        model.addAttribute("bankTransaction", bankTransaction);
        iBankTransationService.userToBank(bankTransaction);
        return "profile";
    }
}
