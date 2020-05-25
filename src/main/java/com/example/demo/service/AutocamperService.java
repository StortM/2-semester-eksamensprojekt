package com.example.demo.service;

import com.example.demo.repository.AutocamperRepositoryImpl;
import com.example.demo.model.Autocamper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class AutocamperService {
    private AutocamperRepositoryImpl autocamperRepositoryImpl;

    public AutocamperService() {
    }

    public Collection<Autocamper> fetchAvailableAutocampersByDates(LocalDate periodStart, LocalDate periodEnd) {
        Collection<Autocamper> listToReturn = new ArrayList<>();

        return listToReturn;
    }

    public Collection<Autocamper> readAll() {
        Collection<Autocamper> listToReturn = new ArrayList<>();

        return listToReturn;
    }
}
