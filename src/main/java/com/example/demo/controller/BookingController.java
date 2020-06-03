package com.example.demo.controller;

import com.example.demo.service.*;
import com.example.demo.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.TreeMap;

/*
    BookingController klassen håndter alt der har med bookinger at gøre.
    Lige fra nye bookinger, hvis mappings har prefix /booking/new/
    og det at kunne se en liste over nuværende bookinger i systemet.

    Der er undervejs i klassen kommenteret hvad hver metode gør og hvilken del
    af bookingprocessen den repræsenterer, for at give overblik.

    En vigtig detalje er at @SessionAttributes benyttes til at gemme objektet
    "bookingBeingCreated" som repræsenterer en ny booking og
    "filteredAutocamperMap" som repræsenterer en liste af ledige autocampere,
    i session. På den måde persister objekterne imellem metoder og
    navigering imellem denne Controller og f.eks. CustomerController
    som involveres når en eksisterende kunde skal kobles til en ny booking.

    Hovedansvarlig: Michael Fuglø & Mads Christensen
 */


@SessionAttributes({"bookingBeingCreated", "filteredAutocamperMap"})
@Controller
public class BookingController {

    private IBookingService bookingService;
    private ICustomerService customerService;
    private IAutocamperService autocamperService;

    public BookingController() {
        bookingService = new BookingServiceImpl();
        customerService = new CustomerServiceImpl();
        autocamperService = new AutocamperServiceImpl();
    }

    /*
    Step 1: Fjern sessionAttributes for at "resette"
            og starte bookingen med friske objekter uden data.
     */
    @GetMapping("/booking/new")
    public String initializeBookingProcess(HttpSession httpSession) {
        httpSession.removeAttribute("bookingBeingCreated");
        httpSession.removeAttribute("filteredAutocamperMap");

        return "redirect:/booking/new/date";
    }

    /*
    Step 2: Kunden præsenteres for en form hvor der kan indtastes fra og til dato for bookingen.
     */
    @GetMapping("/booking/new/date")
    public String displayChooseDateForm(Model model) {
        model.addAttribute("title", "Vælg Dato");
        model.addAttribute("bookingBeingCreated", new Booking());
        model.addAttribute("filteredAutocamperMap", new TreeMap<Integer, Autocamper>());

        return "/booking/new/date";
    }

    /*
    Step 2.1: Booking objektet hentes via httpSession objektet.

              Indtastede datoer gemmes i booking objektet fra sessionen.
              Filtreret map over ledige autocampers for den givne periode
              hentes ved hjælp fra servicelaget.

              Datoer tages imod som strings da Spring ellers giver en exception
              (nok pga. dato formatet). De bliver herefter parset til LocalDates
              via LocalDate.parse() metoden
     */
    @PostMapping("/booking/new/date")
    public String processDateForm(@RequestParam String periodStart, @RequestParam String periodEnd, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        Booking bookingBeingCreated = (Booking) httpSession.getAttribute("bookingBeingCreated");

        LocalDate periodStartAsLocalDate = LocalDate.parse(periodStart);
        LocalDate periodEndAsLocalDate = LocalDate.parse(periodEnd);

        bookingBeingCreated.setPeriodStart(periodStartAsLocalDate);
        bookingBeingCreated.setPeriodEnd(periodEndAsLocalDate);

        redirectAttributes.addFlashAttribute(("filteredAutocamperMap"), autocamperService.getFilteredMapByPeriod(periodStartAsLocalDate, periodEndAsLocalDate));

        return "redirect:/booking/new/autocamper";
    }

    /*
    Step 3: Systemet viser listen fra det forrige trin.
            RedirectAttributes fra forrige metode injects automatisk i modellen,
            hvilket også gør at TreeMap objektet der er annoteret som @SessionAttribute
            opdateres med den filtrerede data.
            Tanken bag at gemme det filtrerede map som SessionAttribute
            er at man i sin browser har mulighed for at gå tilbage til listen over mulige autocampere,
            efter at man er havnet på siden med kundeinformation.
            Det ville ikke være muligt hvis der kun blev brugt RedirectAttribute
            da alle attributes i RedirectAttribute objektet kun persister
            fra metoden der redirecter, til metoden som redirectionen er mappet til.
     */
    @GetMapping("/booking/new/autocamper")
    public String displayAvailableAutocampers(Model model) {
        model.addAttribute("title", "Ledige Autocampere");

        return "/booking/new/available";
    }

    /*
    Step 3.1: En autocamper bliver valgt og tilføjet til bookingen.
              Controlleren redirecter til kundedelen af bookingen hvor man kan tilføje eksisterende
              eller oprette ny kunde og tilknytte til ordren.
     */
    @PostMapping("/booking/new/autocamper")
    public String processAddAutocamper(@RequestParam int id, HttpSession httpSession) {
        Booking bookingBeingCreated = (Booking) httpSession.getAttribute("bookingBeingCreated");

        bookingBeingCreated.setAutocamperId(id);

        return "redirect:/booking/new/customer";
    }

    /*
    Step 4: En form vises med mulighed for at indtaste kundeoplysninger,
            samt en knap til hvis man ønsker at tilknytte en eksisterende kunde.
            Valg af eksisterende kunde er mappet til /booking/new/customerExisting
     */
    @GetMapping("/booking/new/customer")
    public String displayCustomerForm(Model model) {
        model.addAttribute("title", "Tilføj Kunde");

        return "/booking/new/customer";
    }

    /*
    Step 4.1: Formen er udfyldt og input behandles af denne PostMapping.
              Et kunde objekt laves ud fra de forskellige request parameters.
              Objektet får et id via getLast() metoden fra customerService.
              Kunde id tilføjes til igangværende booking objekt.
     */
    @PostMapping("/booking/new/customer")
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

        bookingBeingCreated.setCustomerId(customerService.generateCustomerFromCustomerForm(firstName, lastName, phone, mail, zipCode, city, address).getId());

        model.addAttribute(bookingBeingCreated);

        return "redirect:/booking/new/locations";
    }

    /*
    Step 4.2: Bliver kaldt hvis man vælger at tilføje en eksisterende kunde til bookingen.
              Returnerer samme liste som /kunder/list og tilføjer "bookingInProgress" variablen med værdien "yes"
              til modellen for at kommunikere med thymeleaf om hvilke knapper der skal loades ind i viewet.
     */
    @GetMapping("/booking/new/customerExisting")
    public String displayCustomerList(Model model) {
        model.addAttribute("title", "Tilføj Kunde");
        model.addAttribute("bookingInProgress", "yes");
        model.addAttribute("customerList", customerService.getAll());

        return "/kunder/list";
    }

    /*
    Step 4.3: Håndterer at tilføje den valgte eksisterende kunde til bookingen
     */
    @PostMapping("/booking/new/customerExisting")
    public String processCustomerExisting(@RequestParam int id, HttpSession httpSession) {
        Booking bookingBeingCreated = (Booking) httpSession.getAttribute("bookingBeingCreated");
        bookingBeingCreated.setCustomerId(id);

        return "redirect:/booking/new/locations";
    }

    /*
    Step 5: Et view præsenteres med mulighed for at indtaste pick-up samt drop-off point.
     */
    @GetMapping("/booking/new/locations")
    public String displayLocationsForm(Model model) {
        model.addAttribute("title", "Tilføj Pick-up og Drop-off");

        return "/booking/new/locations";
    }

    /*
    Step 5.1: Indtastet input fra formen tilføjes til booking objektet.
     */
    @PostMapping("/booking/new/locations")
    public String processLocationsForm(Model model,
                                      @RequestParam String pickUp,
                                      @RequestParam String dropOff,
                                      HttpSession httpSession) {
        model.addAttribute("title", "Oversigt");
        Booking bookingBeingCreated = (Booking) httpSession.getAttribute("bookingBeingCreated");

        bookingBeingCreated.setPickUp(pickUp);
        bookingBeingCreated.setDropOff(dropOff);

        model.addAttribute(bookingBeingCreated);

        return "redirect:/booking/new/overview";
    }

    /*
    Step 6: Et overblik over bookingen vises.
            Det er et sidste "checkpoint" hvor informationer
            der er gemt i booking objektet bliver præsenteret på overskuelig vis.
            Man kan herfra trykke på en knap for at oprette bookingen
            hvis alle informationer stemmer.
     */
    @GetMapping("/booking/new/overview")
    public String displayBookingOverview(Model model, HttpSession httpSession) {
        model.addAttribute("title", "Booking Overblik");

        Booking bookingBeingCreated = (Booking) httpSession.getAttribute("bookingBeingCreated");
        bookingBeingCreated.setPriceTotal(bookingService.getTotalPrice(bookingBeingCreated));
        model.addAttribute(bookingBeingCreated);

        return "/booking/new/overview";
    }

    /*
    Step 7: Sidste del af bookingen.
            Viewet viser en success besked samt booking id
            der er blevet tilknyttet objektet.
            Bookingen gemmes via servicelaget til databasen
            og kan nu hentes frem igen på et senere tidspunkt.

            sessionStatus.setComplete() kaldes for at tillade at SessionAttributes kan garbage collectes
     */
    @GetMapping("/booking/new/complete")
    public String displayBookingCompleted(Model model, HttpSession httpSession, SessionStatus sessionStatus) {
        model.addAttribute("title", "Booking Oprettet");

        Booking bookingBeingCreated = (Booking) httpSession.getAttribute("bookingBeingCreated");

        bookingBeingCreated.setId(bookingService.calculateBookingId());
        bookingService.add(bookingBeingCreated);

        model.addAttribute(bookingBeingCreated);

        sessionStatus.setComplete();

        return "/booking/new/complete";
    }

    /*
    Returnerer en liste over bookinger i systemet. Ikke en del af ny booking flowet.
     */
    @GetMapping("/booking/list")
    public String displayBookingList(Model model) {
        model.addAttribute("title", "Bookinger");
        model.addAttribute("bookingList", bookingService.getAll());

        return "/booking/list";
    }
}