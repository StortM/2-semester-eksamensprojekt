package com.example.demo.controller;

import com.example.demo.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
    Controller der håndterer kundedelen af applikationen.

    Hovedansvarlig: Mads Christensen
 */

@Controller
public class CustomerController {
    private ICustomerService customerService;

    public CustomerController() {
        customerService = new CustomerServiceImpl();
    }

    /* Kaldes både i bookingprocessen og hvis man bare ønsker at se kunder i systemet.
        Thymeleaf forventer en attribute i modellen som fortæller hvor /kunder/list.html hentes fra.
        På den måde kan indhold på siden dannes alt efter hvilken sammenhæng som viewet bruges i.
     */
    @GetMapping("/kunder")
    public String displayCustomerList(Model model) {
        model.addAttribute("title", "Kunder");
        model.addAttribute("customerList", customerService.getAll());

        return "/kunder/list";
    }
}
