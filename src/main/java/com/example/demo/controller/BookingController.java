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
        List<AutocamperDTO> allAutocamperList = autocamperDAO.readAll();
        List<BookingDTO> allBookingList = bookingDAO.readAll();
        List<Integer> removedIds = new ArrayList<>();

        System.out.println(bookingDTO);

        if (!allBookingList.isEmpty())
            for (int i = 1; i <= allBookingList.size(); i++) {
                BookingDTO bookingFromDB = allBookingList.get(i - 1);

                // https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap forklaring på sortering af ledige autocampere
                // Mangler at tjekke for om start/slut dato er det samme
                if (!removedIds.contains(bookingFromDB.getAutocamperId() - 1)) {
                    if (bookingDTO.getPeriodStart().isBefore(bookingFromDB.getPeriodEnd()) && bookingDTO.getPeriodEnd().isAfter(bookingFromDB.getPeriodStart())
                        || bookingDTO.getPeriodStart().equals(bookingFromDB.getPeriodStart()) && bookingDTO.getPeriodEnd().equals(bookingFromDB.getPeriodEnd())) {
                        removedIds.add(bookingFromDB.getAutocamperId() - 1);
                        allAutocamperList.remove(bookingFromDB.getAutocamperId() - 1);
                    }
                }
            }

        model.addAttribute("autocampers", allAutocamperList);

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
