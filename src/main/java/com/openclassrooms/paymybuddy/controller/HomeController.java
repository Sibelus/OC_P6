package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/home")
    public String welcomePage(Model model) {
        User currentUser = iUserService.getCurrentUser();
        String welcomMessage = "Welcom " + currentUser.getFirstname() + " " + currentUser.getLastname();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("welcomMessage", welcomMessage);
        return "home";
    }


    @GetMapping("/homeOauth2")
    public String oauthConnexion(Model model){
        return "homeOauth2";
    }

}
