package com.example.demo.controller;

import com.example.demo.repository.CustomerDAO;
import com.example.demo.repository.DatabaseConnectionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

@Controller
public class CustomerController {
    CustomerDAO customerDAO;

    public CustomerController() throws SQLException {
        customerDAO = new CustomerDAO(DatabaseConnectionManager.getInstance().getDatabaseConnection());
    }

    @GetMapping("/kunder")
    public String displayCustomerList(Model model) {
        model.addAttribute("title", "Kunder");
        model.addAttribute("customerList", customerDAO.readAll());

        return "/kunder/list";
    }
}
