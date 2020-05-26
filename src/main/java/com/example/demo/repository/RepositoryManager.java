package com.example.demo.repository;

import java.sql.SQLException;

public class RepositoryManager {
    private static RepositoryManager instance;
    private IAutocamperRepository autocamperRepository;
    private IBookingRepository bookingRepository;
    private ICustomerRepository customerRepository;

    private RepositoryManager() throws SQLException {
        autocamperRepository = new AutocamperRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
        bookingRepository = new BookingRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
        customerRepository = new CustomerRepositoryImpl(DatabaseConnectionManager.getInstance().getDatabaseConnection());
    }

    public static RepositoryManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new RepositoryManager();
        }

        return instance;
    }

    public IAutocamperRepository getAutocamperRepository() {
        return autocamperRepository;
    }

    public IBookingRepository getBookingRepository() {
        return bookingRepository;
    }

    public ICustomerRepository getCustomerRepository() {
        return customerRepository;
    }
}
