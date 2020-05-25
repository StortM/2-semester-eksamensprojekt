package com.example.demo.controller;

import com.example.demo.model.Autocamper;
import com.example.demo.model.Booking;
import com.example.demo.model.Customer;
import com.example.demo.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Controller
public class BookingController {

    private AutocamperRepositoryImpl autocamperRepositoryImpl;
    private BookingRepositoryImpl bookingRepositoryImpl;
    private CustomerRepositoryImpl customerRepositoryImpl;
    private Booking bookingBeingCreated;

    public BookingController() throws SQLException {
        autocamperRepositoryImpl = new AutocamperRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
        bookingRepositoryImpl = new BookingRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
        customerRepositoryImpl = new CustomerRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
    }

    @GetMapping("/booking")
    public String displayChooseDateForm(Model model, HttpSession httpSession) {
        model.addAttribute("title", "Vælg Dato");

        Booking tempBooking = new Booking();
        tempBooking.setPriceTotal(8000);

        httpSession.setAttribute("newBooking", tempBooking);

        bookingBeingCreated = new Booking();
        model.addAttribute("bookingBeingCreated", bookingBeingCreated);
        return "/booking/date";
    }

    @GetMapping("/booking/list")
    public String displayBookingList(Model model) {
        model.addAttribute("title", "Bookinger");
        model.addAttribute("bookingList", bookingRepositoryImpl.readAll());

        /* Kan bruges hvis man vil tilføje kunde navn og lign. til tabellen
        model.addAttribute("customerList", customerRepositoryImpl.readAll());
        model.addAttribute("autocamperList", autocamperDAO.readAll());

         */

        return "/booking/list";
    }

    @PostMapping("/booking/available")
    public String displayAvailableAutocampersList(@RequestParam String periodStart, @RequestParam String periodEnd, Model model, HttpSession httpSession, SessionStatus sessionStatus) {
        model.addAttribute("title", "Ledige Autocampere");

        LocalDate periodStartAsDate = LocalDate.parse(periodStart);
        LocalDate periodEndAsDate = LocalDate.parse(periodEnd);

        bookingBeingCreated.setPeriodStart(periodStartAsDate);
        bookingBeingCreated.setPeriodEnd(periodEndAsDate);

        Map<Integer, Autocamper> autocamperMap = autocamperRepositoryImpl.readAllAsMap();
        List<Booking> bookingList = (List<Booking>) bookingRepositoryImpl.readAll();

        for(int i = 1; i <= bookingList.size(); i++) {
            Booking currentBookingDB = bookingList.get(i - 1);

            if((periodStartAsDate.isBefore(currentBookingDB.getPeriodEnd())
                    && periodEndAsDate.isAfter(currentBookingDB.getPeriodStart()))) {

                autocamperMap.remove(currentBookingDB.getAutocamperId());
            }
        }

        Booking booking = (Booking) httpSession.getAttribute("newBooking");

        booking.setPickUp("qtqqtqtqqt");
        System.out.println(booking);

        model.addAttribute("autocamperList", autocamperMap);

        return "/booking/available";
    }

    @GetMapping("/booking/addAutocamper")
    public String addAutocamper(@RequestParam int id) {
        this.bookingBeingCreated.setAutocamperId(id);

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

        Customer customerToBeCreated = new Customer(firstName, lastName, phone, mail, zipCode, city, address);
        customerRepositoryImpl.create(customerToBeCreated);

        customerToBeCreated.setId(customerRepositoryImpl.readLast().getId());

        bookingBeingCreated.setCustomerId(customerToBeCreated.getId());

        int bookingId = bookingRepositoryImpl.readLast().getId() + 1;
        bookingBeingCreated.setId(bookingId);
        bookingRepositoryImpl.create(bookingBeingCreated);

        model.addAttribute(bookingBeingCreated);

        return "/booking/overview";
    }
}