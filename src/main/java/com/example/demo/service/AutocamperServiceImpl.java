package com.example.demo.service;

import com.example.demo.model.Booking;
import com.example.demo.model.Autocamper;
import com.example.demo.repository.RepositoryManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AutocamperServiceImpl implements IAutocamperService {

    public AutocamperServiceImpl() {
    }

    @Override
    public void add(Autocamper autocamper) {
        try {
            RepositoryManager.getInstance().getAutocamperRepository().create(autocamper);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Autocamper get(int id) {
        try {
            return RepositoryManager.getInstance().getAutocamperRepository().read(id);
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(Autocamper autocamper) {
        try {
            RepositoryManager.getInstance().getAutocamperRepository().update(autocamper);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        try {
            RepositoryManager.getInstance().getAutocamperRepository().delete(id);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Autocamper> getAll() {
        try {
            return RepositoryManager.getInstance().getAutocamperRepository().readAll();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
        Metode som returnerer et map af autocampere som ikke er booket i en given periode.
        Metoden returnerer et map og ikke en liste da det gjorde implementationen nemmere og bedre.
        Med en liste som f.eks. en ArrayList vil det at fjerne autocampere ændre på autocamperens index
        og der vil på den måde være et skel imellem autocamper objektets index i listen og det id der findes
        i databasen. Derudover starter en liste fra index 0 hvilket heller ikke er repræsentativt for databasen.
        Ved at gemme autocamper objekter i et map undgår vi disse problemer da en key i mappet,
        med et givent autocamper id, ikke vil ændres når et Entry fjernes fra mappet.
     */
    @Override
    public Map<Integer, Autocamper> getFilteredMapByPeriod(LocalDate periodStart, LocalDate periodEnd) {
        Map<Integer, Autocamper> autocamperMapToReturn;
        List<Booking> bookingList;

        try {
            autocamperMapToReturn = RepositoryManager.getInstance().getAutocamperRepository().readAllAsMap();
            bookingList = RepositoryManager.getInstance().getBookingRepository().readAll();

            for(int i = 1; i <= bookingList.size(); i++) {
                Booking currentBookingFromDatabase = bookingList.get(i - 1);

                if((periodStart.isBefore(currentBookingFromDatabase.getPeriodEnd())
                        && periodEnd.isAfter(currentBookingFromDatabase.getPeriodStart()))) {

                    autocamperMapToReturn.remove(currentBookingFromDatabase.getAutocamperId());
                }
            }

            return autocamperMapToReturn;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
