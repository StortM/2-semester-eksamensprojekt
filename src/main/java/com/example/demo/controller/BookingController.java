package com.example.demo.controller;

import com.example.demo.service.*;
import com.example.demo.repository.*;
import com.example.demo.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;

@SessionAttributes("bookingBeingCreated")
@Controller
public class BookingController {

    // private IAutocamperRepository autocamperRepository;
    private IBookingRepository bookingRepository;
    private ICustomerRepository customerRepository;
    private IAutocamperService autocamperService;

    public BookingController() throws SQLException {
        // autocamperRepository = new AutocamperRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
        bookingRepository = new BookingRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
        customerRepository = new CustomerRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
        autocamperService = new AutocamperServiceImpl();
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

        LocalDate periodStartAsLocalDate = LocalDate.parse(periodStart);
        LocalDate periodEndAsLocalDate = LocalDate.parse(periodEnd);

        bookingBeingCreated.setPeriodStart(periodStartAsLocalDate);
        bookingBeingCreated.setPeriodEnd(periodEndAsLocalDate);

        model.addAttribute("filteredAutocamperMap", autocamperService.getSortedListByPeriod(periodStartAsLocalDate, periodEndAsLocalDate));

        return "/booking/available";
    }

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

        model.addAttribute(bookingBeingCreated);

        return "redirect:/booking/locations";
    }

    @GetMapping("/booking/locations")
    public String displayLocationsForm(Model model) {
        model.addAttribute("title", "Tilføj lokationer");

        return "/booking/locations";
    }

    @PostMapping("/booking/locations")
    public String processLocationsForm(Model model,
                                      @RequestParam String pickUp,
                                      @RequestParam String dropOff,
                                      HttpSession httpSession) {
        model.addAttribute("title", "Oversigt");
        Booking bookingBeingCreated = (Booking) httpSession.getAttribute("bookingBeingCreated");

        bookingBeingCreated.setPickUp(pickUp);
        bookingBeingCreated.setDropOff(dropOff);

        model.addAttribute(bookingBeingCreated);

        int bookingId = bookingRepository.readLast().getId() + 1;
        bookingBeingCreated.setId(bookingId);
        bookingRepository.create(bookingBeingCreated);

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
        model.addAttribute("filteredAutocamperMap", autocamperDAO.readAll());

         */

        return "/booking/list";
    }
}