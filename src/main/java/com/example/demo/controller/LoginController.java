package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
    Helt simpel login controller som også fungerer som startpunkt.

    index mapping er sat til at returnere home, hvilket sker hvis brugeren er authenticated.
    Ellers afviser Spring Security requesten og redirecter til login siden på /login

    home er altså en lukket del der kræver login og login siden er "index" siden for
    sessions uden en authenticated bruger tilknyttet.
 */

@Controller
public class LoginController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("title", "Hjem");
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("title", "Log Ind");
        return ("login");
    }
}