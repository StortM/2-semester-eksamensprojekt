package com.example.demo.service;

import com.example.demo.model.Autocamper;

import java.time.LocalDate;
import java.util.Map;

public interface IAutocamperService extends IService<Autocamper>{

    Map<Integer, Autocamper> getSortedListByPeriod(LocalDate periodStart, LocalDate periodEnd);
}
