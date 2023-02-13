package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.*;
import com.openclassrooms.paymybuddy.service.IInappTransactionService;
import com.openclassrooms.paymybuddy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TransferController {

    @Autowired
    IUserService iUserService;
    @Autowired
    IInappTransactionService iInappTransactionService;

    @GetMapping("/transfer")
    public String transferPage(Model model) {
        InAppTransaction inAppTransaction = new InAppTransaction();
        User currentUser = iUserService.getCurrentUser();
        List<Connection> friends = currentUser.getFriendsList();
        List<InAppTransaction> inAppTransactions = currentUser.getInAppTransactions();
        List<BankTransaction> bankTransactions = currentUser.getBankTransactions();

        model.addAttribute("friends", friends);
        model.addAttribute("inAppTransaction", inAppTransaction);
        model.addAttribute("inAppTransactions", inAppTransactions);
        model.addAttribute("bankTransactions", bankTransactions);
        return "transfer";
    }


    @PostMapping("/transfer")
    public String bankToUserSubmit(@ModelAttribute InAppTransaction inAppTransaction, Model model){
        User currentUser = iUserService.getCurrentUser();
        List<Connection> friendsList = currentUser.getFriendsList();
        List<InAppTransaction> inAppTransactions = currentUser.getInAppTransactions();
        List<BankTransaction> bankTransactions = currentUser.getBankTransactions();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("friendList", friendsList);
        model.addAttribute("inAppTransaction", inAppTransaction);
        model.addAttribute("inAppTransactions", inAppTransactions);
        model.addAttribute("bankTransactions", bankTransactions);

        inAppTransaction.setSender(currentUser);
        iInappTransactionService.sendMoneyToFriend(inAppTransaction);
        return "transfer";
    }


    /*
    @PostMapping("/transfer/{friendId},{amount},{comment}")
    public String transferFriendSubmit(@PathVariable String friendId, @PathVariable String amount, @PathVariable String comment, Model model) {
        model.addAttribute("friendId", friendId);
        model.addAttribute("amount", amount);
        model.addAttribute("comment", comment);
        iInappTransactionService.sendMoneyToFriend(Integer.parseInt(friendId), Integer.parseInt(amount), comment);
        return "transfer";
    }


    @PostMapping("/transfer")
    public String transferFriendSubmit(@RequestParam(value = "friendId") int friendId, @RequestParam(name = "amount") int amount, @RequestParam(name = "comment") String comment, Model model) {
        model.addAttribute("friendId", friendId);
        model.addAttribute("amount", amount);
        model.addAttribute("comment", comment);
        iInappTransactionService.sendMoneyToFriend(friendId, amount, comment);
        return "transfer";
    }*/
}
