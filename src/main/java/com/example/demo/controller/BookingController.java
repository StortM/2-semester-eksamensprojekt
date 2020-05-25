package com.example.demo.controller;

import com.example.demo.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@SessionAttributes("bookingBeingCreated")
@Controller
public class BookingController {

    private AutocamperDAO autocamperDAO;
    private BookingDAO bookingDAO;
    private CustomerDAO customerDAO;
    // private BookingDTO bookingBeingCreated;

    public BookingController() throws SQLException {
        autocamperDAO = new AutocamperDAO(DatabaseConnectionManager.getInstance().getDatabaseConnection());
        bookingDAO = new BookingDAO(DatabaseConnectionManager.getInstance().getDatabaseConnection());
        customerDAO = new CustomerDAO(DatabaseConnectionManager.getInstance().getDatabaseConnection());
    }

    @GetMapping("/booking")
    public String displayChooseDateForm(Model model, HttpSession httpSession) {
        httpSession.removeAttribute("bookingBeingCreated");


        model.addAttribute("title", "Vælg Dato");
        model.addAttribute("bookingBeingCreated", new BookingDTO());

        return "/booking/date";
    }

    @GetMapping("/booking/list")
    public String displayBookingList(Model model) {
        model.addAttribute("title", "Bookinger");
        model.addAttribute("bookingList", bookingDAO.readAll());

        /* Kan bruges hvis man vil tilføje kunde navn og lign. til tabellen
        model.addAttribute("customerList", customerDAO.readAll());
        model.addAttribute("autocamperList", autocamperDAO.readAll());

         */

        return "/booking/list";
    }

    @PostMapping("/booking/available")
    public String displayAvailableAutocampersList(@RequestParam String periodStart, @RequestParam String periodEnd, Model model, HttpSession httpSession) {
        model.addAttribute("title", "Ledige Autocampere");
        BookingDTO bookingBeingCreated = (BookingDTO) httpSession.getAttribute("bookingBeingCreated");

        LocalDate periodStartAsDate = LocalDate.parse(periodStart);
        LocalDate periodEndAsDate = LocalDate.parse(periodEnd);

        bookingBeingCreated.setPeriodStart(periodStartAsDate);
        bookingBeingCreated.setPeriodEnd(periodEndAsDate);

        Map<Integer, AutocamperDTO> autocamperMap = autocamperDAO.readAllAsMap();
        List<BookingDTO> bookingList = (List<BookingDTO>) bookingDAO.readAll();

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
    public String addAutocamper(@RequestParam int id, HttpSession httpSession) {
        BookingDTO bookingBeingCreated = (BookingDTO) httpSession.getAttribute("bookingBeingCreated");
        bookingBeingCreated.setAutocamperId(id);

        return "redirect:/booking/customer";
    }

    @GetMapping("/booking/customer")
    public String displayCustomerForm(Model model) {
        model.addAttribute("title", "Tilføj Kunde");

        return "/booking/customer";
    }

    @PostMapping("/booking/customer")
    public String processCustomerForm(Model model,
                                      @RequestParam String firstName,
                                      @RequestParam String lastName,
                                      @RequestParam int phone,
                                      @RequestParam String mail,
                                      @RequestParam int zipCode,
                                      @RequestParam String city,
                                      @RequestParam String address, HttpSession httpSession) {

        model.addAttribute("title", "Oversigt");
        BookingDTO bookingBeingCreated = (BookingDTO) httpSession.getAttribute("bookingBeingCreated");

        CustomerDTO customerToBeCreated = new CustomerDTO(firstName, lastName, phone, mail, zipCode, city, address);
        customerDAO.create(customerToBeCreated);

        customerToBeCreated.setId(customerDAO.readLast().getId());

        bookingBeingCreated.setCustomerId(customerToBeCreated.getId());

        int bookingId = bookingDAO.readLast().getId() + 1;
        bookingBeingCreated.setId(bookingId);
        bookingDAO.create(bookingBeingCreated);

        model.addAttribute(bookingBeingCreated);

        return "redirect:/booking/overview";
    }

    @ModelAttribute("bookingBeingCreated")
    @GetMapping("/booking/overview")
    public String displayBookingOverview() {

        return "/booking/overview";
    }
}