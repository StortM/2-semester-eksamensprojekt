package com.example.demo.service;

import com.example.demo.model.Booking;
import com.example.demo.repository.AutocamperRepositoryImpl;
import com.example.demo.model.Autocamper;
import com.example.demo.repository.BookingRepositoryImpl;
import com.example.demo.repository.DatabaseConnectionManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AutocamperServiceImpl implements IAutocamperService {
    private AutocamperRepositoryImpl autocamperRepositoryImpl;
    private BookingRepositoryImpl bookingRepositoryImpl;

    public AutocamperServiceImpl() throws SQLException {
        autocamperRepositoryImpl = new AutocamperRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
        bookingRepositoryImpl = new BookingRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
    }

    public void add(Autocamper autocamper) {
        // skal laves
    }

    public Autocamper get(int id) {
        // skal laves
        return new Autocamper();
    }

    public void update(Autocamper autocamper) {
        // skal laves
    }

    public void remove(int id) {
        // skal laves
    }

    public Map<Integer, Autocamper> getSortedListByPeriod(LocalDate periodStart, LocalDate periodEnd) {
        Map<Integer, Autocamper> autocamperMapToReturn = autocamperRepositoryImpl.readAllAsMap();
        List<Booking> bookingList = (List<Booking>) bookingRepositoryImpl.readAll();

        for(int i = 1; i <= bookingList.size(); i++) {
            Booking currentBookingFromDatabase = bookingList.get(i - 1);

            if((periodStart.isBefore(currentBookingFromDatabase.getPeriodEnd())
                    && periodEnd.isAfter(currentBookingFromDatabase.getPeriodStart()))) {

                autocamperMapToReturn.remove(currentBookingFromDatabase.getAutocamperId());
            }
        }

        return autocamperMapToReturn;
    }

    public Collection<Autocamper> getAll() {
        Collection<Autocamper> listToReturn = new ArrayList<>();

        return listToReturn;
    }
}
