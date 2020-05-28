package com.example.demo.service;

import com.example.demo.model.Booking;

public interface IBookingService extends IService<Booking> {
    Booking getLast();
    int getTotalPrice(Booking booking);
    int calculateBookingId();
}
