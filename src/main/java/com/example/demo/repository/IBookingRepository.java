package com.example.demo.repository;

import com.example.demo.model.Booking;

public interface IBookingRepository extends ICRUDRepository<Booking>{
    public Booking readLast();
}
