package com.example.demo.controller;

import com.example.demo.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class BookingController {

    private AutocamperDAO autocamperDAO;
    private BookingDAO bookingDAO;
    private CustomerDAO customerDAO;
    private BookingDTO bookingBeingCreated;

    public BookingController() throws SQLException {
        autocamperDAO = new AutocamperDAO(DatabaseConnectionManager.getInstance().getDatabaseConnection());
        bookingDAO = new BookingDAO(DatabaseConnectionManager.getInstance().getDatabaseConnection());
        customerDAO = new CustomerDAO(DatabaseConnectionManager.getInstance().getDatabaseConnection());
    }


    @GetMapping("/booking")
    public String displayChooseDateForm(Model model) {
        bookingBeingCreated = new BookingDTO();
        model.addAttribute("bookingBeingCreated", bookingBeingCreated);
        return "/booking/date";
    }

    @PostMapping("/booking/available")
    public String displayAvailableAutocampersList(@RequestParam String periodStart, @RequestParam String periodEnd, Model model) {
        LocalDate periodStartAsDate = LocalDate.parse(periodStart);
        LocalDate periodEndAsDate = LocalDate.parse(periodEnd);

        bookingBeingCreated.setPeriodStart(periodStartAsDate);
        bookingBeingCreated.setPeriodEnd(periodEndAsDate);

        Map<Integer, AutocamperDTO> autocamperMap = autocamperDAO.readAllAsMap();
        List<BookingDTO> bookingList = bookingDAO.readAll();

        for(int i = 1; i <= bookingList.size(); i++) {
            BookingDTO currentBookingDB = bookingList.get(i - 1);

            if((periodStartAsDate.isBefore(currentBookingDB.getPeriodEnd())
                    && periodEndAsDate.isAfter(currentBookingDB.getPeriodStart()))) {

                autocamperMap.remove(currentBookingDB.getAutocamperId());
            }
        }

        model.addAttribute("autocamperList", autocamperMap);

        return "/booking/available";
    }

    @GetMapping("/booking/addAutocamper")
    public String addAutocamper(@RequestParam int id) {
        bookingBeingCreated.setAutocamperId(id);

        return "redirect:/booking/customer";
    }

    @GetMapping("/booking/customer")
    public String displayCustomerForm() {

        return "/booking/customer";
    }

    @PostMapping("/booking/customer")
    public String processCustomerForm(
            @RequestParam String firstName, @RequestParam String lastName, @RequestParam int phone,
            @RequestParam String mail, @RequestParam int zipCode, @RequestParam String city, @RequestParam String address) {

        CustomerDTO customerToBeCreated = new CustomerDTO(firstName, lastName, phone, mail, zipCode, city, address);
        customerDAO.create(customerToBeCreated);

        customerToBeCreated.setId(customerDAO.readLast().getId());

        bookingBeingCreated.setCustomerId(customerToBeCreated.getId());

        return "/booking/overview";
    }
}