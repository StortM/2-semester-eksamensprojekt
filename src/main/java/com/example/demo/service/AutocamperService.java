package com.example.demo.service;

import com.example.demo.repository.AutocamperDAO;
import com.example.demo.repository.AutocamperDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AutocamperService {
    private AutocamperDAO autocamperDAO;

    public AutocamperService() {
    }

    public Collection<AutocamperDTO> fetchAvailableAutocampersByDates(LocalDate periodStart, LocalDate periodEnd) {
        Collection<AutocamperDTO> listToReturn = new ArrayList<>();

        return listToReturn;
    }

    public Collection<AutocamperDTO> readAll() {
        Collection<AutocamperDTO> listToReturn = new ArrayList<>();

        return listToReturn;
    }
}
