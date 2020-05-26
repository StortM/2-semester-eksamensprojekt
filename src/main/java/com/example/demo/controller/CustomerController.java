package com.example.demo.controller;

import com.example.demo.repository.CustomerRepositoryImpl;
import com.example.demo.repository.DatabaseConnectionManager;
import com.example.demo.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

@Controller
public class CustomerController {
    private IBookingService bookingService;
    private ICustomerService customerService;
    private IAutocamperService autocamperService;

    public CustomerController() throws SQLException {
        bookingService = new BookingServiceImpl();
        customerService = new CustomerServiceImpl();
        autocamperService = new AutocamperServiceImpl();
    }

    @GetMapping("/kunder")
    public String displayCustomerList(Model model) {
        model.addAttribute("title", "Kunder");
        model.addAttribute("customerList", customerService.getAll());

        return "/kunder/list";
    }
}
