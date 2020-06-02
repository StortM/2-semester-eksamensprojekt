package com.example.demo.service;

import com.example.demo.model.Booking;
import com.example.demo.repository.RepositoryManager;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.List;

/*
    Service klasse med ansvar for at udfører operationer relevante for en booking.
    Dette gøres via klassens tilsvarende repository som står for kontakt med databasen.
 */
public class BookingServiceImpl implements IBookingService {

    public BookingServiceImpl() {
    }

    /*
        Udregner booking id for ny booking ved at finde nyeste booking row i databasen
        og inkrementere id fra denne med 1.
     */
    @Override
    public int calculateBookingId() {

        return getLast().getId() + 1;
    }

    /*
        Udregner totalpris for en booking.
        Gør dette ved at finde ud af antallet af dage i booking objektets periode
        og gange dette med bookingens tilknyttede autocampers pris pr. dag
     */
    @Override
    public int getTotalPrice(Booking booking) {
        int priceDay = 0;
        int numberOfDays = (int) ChronoUnit.DAYS.between(booking.getPeriodStart(), booking.getPeriodEnd());

        try {
            priceDay = RepositoryManager.getInstance().getAutocamperRepository().read(booking.getAutocamperId()).getPriceDay();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return priceDay * numberOfDays;
    }

    @Override
    public void add(Booking booking) {
        try {
            RepositoryManager.getInstance().getBookingRepository().create(booking);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Booking get(int id) {
        try {
            return RepositoryManager.getInstance().getBookingRepository().read(id);
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(Booking booking) {
        try {
            RepositoryManager.getInstance().getBookingRepository().update(booking);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        try {
            RepositoryManager.getInstance().getBookingRepository().delete(id);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Booking> getAll() {
        try {
            return RepositoryManager.getInstance().getBookingRepository().readAll();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
        Metoden returnerer et Booking objekt som er lavet ud fra det sidste i row i databasen.
        Med andre ord den seneste booking.
        Dette bruges i BookingController klassen til at sætte ID for den nyoprettede booking.
     */
    @Override
    public Booking getLast() {
        try {
            return RepositoryManager.getInstance().getBookingRepository().readLast();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
