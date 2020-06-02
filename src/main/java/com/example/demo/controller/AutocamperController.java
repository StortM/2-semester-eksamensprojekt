package com.example.demo.controller;

import com.example.demo.repository.AutocamperRepositoryImpl;
import com.example.demo.repository.DatabaseConnectionManager;
import com.example.demo.service.AutocamperServiceImpl;
import com.example.demo.service.IAutocamperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

/*
    Controller der håndter alt med autocampere at gøre
 */

@Controller
public class AutocamperController {
    IAutocamperService autocamperService;

    public AutocamperController() {
        autocamperService = new AutocamperServiceImpl();
    }

    @GetMapping("/autocampere")
    public String displayAutocamperList(Model model) {
        model.addAttribute("title", "Autocampere");
        model.addAttribute("autocamperList", autocamperService.getAll());

        return "/autocampere/list";
    }
}
