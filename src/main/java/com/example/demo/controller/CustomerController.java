package com.example.demo.controller;

import com.example.demo.repository.CustomerRepositoryImpl;
import com.example.demo.repository.DatabaseConnectionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

@Controller
public class CustomerController {
    CustomerRepositoryImpl customerRepositoryImpl;

    public CustomerController() throws SQLException {
        customerRepositoryImpl = new CustomerRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
    }

    @GetMapping("/kunder")
    public String displayCustomerList(Model model) {
        model.addAttribute("title", "Kunder");
        model.addAttribute("customerList", customerRepositoryImpl.readAll());

        return "/kunder/list";
    }
}
