package com.example.demo.controller;

import com.example.demo.service.AutocamperServiceImpl;
import com.example.demo.service.IAutocamperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
    Controller der håndter alt med autocampere at gøre

    Hovedansvarlig: Mads Christensen
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
