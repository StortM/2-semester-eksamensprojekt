package com.example.demo.repository;

import com.example.demo.model.Autocamper;

import java.util.Map;

public interface IAutocamperRepository extends ICRUDRepository<Autocamper> {
    //Unique method for AutocamperDAO
    Map<Integer, Autocamper> readAllAsMap();
}
