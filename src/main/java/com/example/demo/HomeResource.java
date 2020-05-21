package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeResource {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/user")
    public String user(){
        return ("user");
    }

    @GetMapping("/admin")
    public String admin(){
        return ("admin");
    }

    @GetMapping("/login")
    public String login(){
        return ("newLogin");
    }
}
