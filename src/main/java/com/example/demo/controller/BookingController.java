package com.example.demo.controller;

import com.example.demo.service.*;
import com.example.demo.repository.*;
import com.example.demo.model.*;
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

    private IAutocamperRepository autocamperRepository;
    private IBookingRepository bookingRepository;
    private ICustomerRepository customerRepository;

    public BookingController() throws SQLException {
        autocamperRepository = new AutocamperRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
        bookingRepository = new BookingRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
        customerRepository = new CustomerRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
    }

    /*
    @ModelAttribute("bookingBeingCreated")
    public BookingDTO addBookingToSession() {

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setPickUp("hej med dig");

        return bookingDTO;
    }
     */

    @GetMapping("/booking")
    public String initializeBookingProcess(HttpSession httpSession) {
        httpSession.removeAttribute("bookingBeingCreated");

        return "redirect:/booking/date";
    }

    @GetMapping("/booking/date")
    public String displayChooseDateForm(Model model, HttpSession httpSession) {

        model.addAttribute("title", "Vælg Dato");
        model.addAttribute("bookingBeingCreated", new Booking());

        return "/booking/date";
    }

    @PostMapping("/booking/available")
    public String displayAvailableAutocampersList(@RequestParam String periodStart, @RequestParam String periodEnd, Model model, HttpSession httpSession) {
        model.addAttribute("title", "Ledige Autocampere");
        Booking bookingBeingCreated = (Booking) httpSession.getAttribute("bookingBeingCreated");

        LocalDate periodStartAsDate = LocalDate.parse(periodStart);
        LocalDate periodEndAsDate = LocalDate.parse(periodEnd);

        bookingBeingCreated.setPeriodStart(periodStartAsDate);
        bookingBeingCreated.setPeriodEnd(periodEndAsDate);

        Map<Integer, Autocamper> autocamperMap = autocamperRepository.readAllAsMap();
        List<Booking> bookingList = (List<Booking>) bookingRepository.readAll();

        for(int i = 1; i <= bookingList.size(); i++) {
            Booking currentBookingDB = bookingList.get(i - 1);

            if((periodStartAsDate.isBefore(currentBookingDB.getPeriodEnd())
                    && periodEndAsDate.isAfter(currentBookingDB.getPeriodStart()))) {

                autocamperMap.remove(currentBookingDB.getAutocamperId());
            }
        }

        model.addAttribute("autocamperList", autocamperMap);

        return "/booking/available";
    }

    /*
    @PostMapping("/booking/available")
    public String displayAvailableAutocampersList(@RequestParam String periodStart, @RequestParam String periodEnd, Model model, HttpSession httpSession) {
        model.addAttribute("title", "Ledige Autocampere");
        System.out.println(httpSession.getAttribute("bookingBeingCreated"));
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

        System.out.println(httpSession.getAttribute("bookingBeingCreated"));

        return "/booking/available";
    }

     */

    @GetMapping("/booking/addAutocamper")
    public String addAutocamper(@RequestParam int id, HttpSession httpSession) {
        Booking bookingBeingCreated = (Booking) httpSession.getAttribute("bookingBeingCreated");
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
        Booking bookingBeingCreated = (Booking) httpSession.getAttribute("bookingBeingCreated");

        Customer customerToBeCreated = new Customer(firstName, lastName, phone, mail, zipCode, city, address);
        customerRepository.create(customerToBeCreated);

        customerToBeCreated.setId(customerRepository.readLast().getId());

        bookingBeingCreated.setCustomerId(customerToBeCreated.getId());

        int bookingId = bookingRepository.readLast().getId() + 1;
        bookingBeingCreated.setId(bookingId);
        bookingRepository.create(bookingBeingCreated);

        model.addAttribute(bookingBeingCreated);

        return "redirect:/booking/overview";
    }

    @GetMapping("/booking/overview")
    public String displayBookingOverview(Model model, HttpSession httpSession) {
        Booking bookingBeingCreated = (Booking) httpSession.getAttribute("bookingBeingCreated");
        model.addAttribute(bookingBeingCreated);

        return "/booking/overview";
    }

    @GetMapping("/booking/list")
    public String displayBookingList(Model model) {
        model.addAttribute("title", "Bookinger");
        model.addAttribute("bookingList", bookingRepository.readAll());

        /* Kan bruges hvis man vil tilføje kunde navn og lign. til tabellen
        model.addAttribute("customerList", customerDAO.readAll());
        model.addAttribute("autocamperList", autocamperDAO.readAll());

         */

        return "/booking/list";
    }
}