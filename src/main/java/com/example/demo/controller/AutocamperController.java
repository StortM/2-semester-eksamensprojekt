package com.example.demo.controller;

import com.example.demo.repository.AutocamperDAO;
import com.example.demo.repository.DatabaseConnectionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

@Controller
public class AutocamperController {
    AutocamperDAO autocamperDAO;

    public AutocamperController() throws SQLException {
        autocamperDAO = new AutocamperDAO(DatabaseConnectionManager.getInstance().getDatabaseConnection());
    }

    @GetMapping("/autocampere")
    public String displayCustomerList(Model model) {
        model.addAttribute("title", "Autocampere");
        model.addAttribute("autocamperList", autocamperDAO.readAll());

        return "/autocampere/list";
    }
}
