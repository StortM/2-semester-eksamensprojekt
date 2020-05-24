package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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