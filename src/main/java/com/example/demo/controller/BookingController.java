package com.example.demo.controller;

import com.example.demo.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    public BookingController() throws SQLException {
        autocamperDAO = new AutocamperDAO(DatabaseConnectionManager.getInstance().getDatabaseConnection());
        bookingDAO = new BookingDAO(DatabaseConnectionManager.getInstance().getDatabaseConnection());
    }

    @GetMapping("/booking")
    public String bookingChooseDate(Model model) {
        model.addAttribute("bookingDTO", new BookingDTO());
        return "/booking/date";
    }

    @PostMapping("/booking/available")
    public String bookingShowAvailable(@ModelAttribute BookingDTO bookingDTO, Model model) {
        Map<Integer, AutocamperDTO> autocamperMap = autocamperDAO.readAllAsMap();
        List<BookingDTO> bookingList = bookingDAO.readAll();

        for(int i = 1; i <= bookingList.size(); i++) {
            BookingDTO currentBookingDB = bookingList.get(i - 1);

            if((bookingDTO.getPeriodStart().isBefore(currentBookingDB.getPeriodEnd())
                    && bookingDTO.getPeriodEnd().isAfter(currentBookingDB.getPeriodStart()))) {

                autocamperMap.remove(currentBookingDB.getAutocamperId());
            }
        }

        model.addAttribute("autocamperList", autocamperMap);

        return "/booking/available";
    }

    /*
    @GetMapping("/booking/available")
    public String bookingShowAvailable(Model model) {
        model.addAttribute("autocampers", autocamperDAO.readAll());

        return "/booking/available";
    }
     */
}
