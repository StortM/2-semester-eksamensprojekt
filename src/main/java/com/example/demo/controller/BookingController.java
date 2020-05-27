package com.example.demo.controller;

import com.example.demo.service.*;
import com.example.demo.repository.*;
import com.example.demo.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;

@SessionAttributes({"bookingBeingCreated", "filteredAutocamperMap"})
@Controller
public class BookingController {

    // private IAutocamperRepository autocamperRepository;
    private IBookingService bookingService;
    private ICustomerService customerService;
    private IAutocamperService autocamperService;

    public BookingController() throws SQLException {
        bookingService = new BookingServiceImpl();
        customerService = new CustomerServiceImpl();
        autocamperService = new AutocamperServiceImpl();
    }

    /*
    Step 1: Fjern sessionAttributes for at der ikke opereres på gammel data
     */
    @GetMapping("/booking")
    public String initializeBookingProcess(HttpSession httpSession) {
        httpSession.removeAttribute("bookingBeingCreated");
        httpSession.removeAttribute("filteredAutocamperMap");

        return "redirect:/booking/date";
    }

    /*
    Step 2: Kunden præsenteres for en form hvor der kan indtastes fra og til dato for bookingen
     */
    @GetMapping("/booking/date")
    public String displayChooseDateForm(Model model) {
        model.addAttribute("title", "Vælg Dato");
        model.addAttribute("bookingBeingCreated", new Booking());

        return "/booking/date";
    }

    /*
    Step 2.1: Indtastede datoer gemmes i booking objektet fra sessionen.
              Filtreret map over ledige autocampers for den givne periode
              hentes ved hjælp fra servicelaget.
     */
    @PostMapping("/booking/date")
    public String processDateForm(@RequestParam String periodStart, @RequestParam String periodEnd, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        Booking bookingBeingCreated = (Booking) httpSession.getAttribute("bookingBeingCreated");

        LocalDate periodStartAsLocalDate = LocalDate.parse(periodStart);
        LocalDate periodEndAsLocalDate = LocalDate.parse(periodEnd);

        bookingBeingCreated.setPeriodStart(periodStartAsLocalDate);
        bookingBeingCreated.setPeriodEnd(periodEndAsLocalDate);

        redirectAttributes.addFlashAttribute(("filteredAutocamperMap"), autocamperService.getFilteredMapByPeriod(periodStartAsLocalDate, periodEndAsLocalDate));

        return "redirect:/booking/autocampers";
    }

    /*
    Step 3: Systemet viser listen fra det forrige trin.
     */

    // Spring injecter automatisk redirectAttributes til modellen
    @GetMapping("/booking/autocampers")
    public String displayAvailableAutocampersList(Model model) {
        model.addAttribute("title", "Ledige Autocampere");

        return "/booking/available";
    }

    /*
    Step 3.1
     */
    @GetMapping("/booking/addAutocamper")
    public String addAutocamper(@ModelAttribute Booking bookingBeingCreated, @RequestParam int id, HttpSession httpSession) {
        bookingBeingCreated.setAutocamperId(id);
        System.out.println(id);
        System.out.println(bookingBeingCreated);

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
        customerService.add(customerToBeCreated);

        customerToBeCreated.setId(customerService.getLast().getId());

        bookingBeingCreated.setCustomerId(customerToBeCreated.getId());

        int bookingId = bookingService.getLast().getId() + 1;
        model.addAttribute(bookingBeingCreated);

        return "redirect:/booking/locations";
    }

    @GetMapping("/booking/existingCustomer")
    public String displayCustomerList(Model model) {
        model.addAttribute("title", "Tilføj Kunde");
        model.addAttribute("bookingInProgress", "yes");
        model.addAttribute("customerList", customerService.getAll());

        return "/kunder/list";
    }

    @GetMapping("/booking/customerProcessExisting")
    public String processCustomerExisting(@RequestParam int id) {
        System.out.println(customerService.get(id));

        return "redirect:/booking";
    }

    @GetMapping("/booking/locations")
    public String displayLocationsForm(Model model) {
        model.addAttribute("title", "Tilføj Pick-up og Drop-off");

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

        int bookingId = bookingService.getLast().getId() + 1;
        bookingBeingCreated.setId(bookingId);
        bookingService.add(bookingBeingCreated);

        model.addAttribute(bookingBeingCreated);

        return "redirect:/booking/overview";
    }

    @GetMapping("/booking/overview")
    public String displayBookingOverview(Model model, HttpSession httpSession) {
        Booking bookingBeingCreated = (Booking) httpSession.getAttribute("bookingBeingCreated");
        model.addAttribute(bookingBeingCreated);

        System.out.println("endelig booking: " + bookingBeingCreated);

        return "/booking/overview";
    }

    @GetMapping("/booking/list")
    public String displayBookingList(Model model) {
        model.addAttribute("title", "Bookinger");
        model.addAttribute("bookingList", bookingService.getAll());

        return "/booking/list";
    }
}