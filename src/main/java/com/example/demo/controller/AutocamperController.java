package com.example.demo.controller;

import com.example.demo.repository.AutocamperRepositoryImpl;
import com.example.demo.repository.DatabaseConnectionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

@Controller
public class AutocamperController {
    AutocamperRepositoryImpl autocamperRepositoryImpl;

    public AutocamperController() throws SQLException {
        autocamperRepositoryImpl = new AutocamperRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
    }

    @GetMapping("/autocampere")
    public String displayCustomerList(Model model) {
        model.addAttribute("title", "Autocampere");
        model.addAttribute("autocamperList", autocamperRepositoryImpl.readAll());

        return "/autocampere/list";
    }
}
