package com.example.demo.service;

import com.example.demo.model.Booking;
import com.example.demo.repository.RepositoryManager;

import java.sql.SQLException;
import java.util.List;

public class BookingServiceImpl implements IBookingService {

    public BookingServiceImpl() {
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
