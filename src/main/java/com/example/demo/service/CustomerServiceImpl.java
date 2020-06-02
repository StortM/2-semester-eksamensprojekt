package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.repository.RepositoryManager;

import java.sql.SQLException;
import java.util.List;

public class CustomerServiceImpl implements ICustomerService {

    public CustomerServiceImpl() {

    }

    /*
        Metoden har til ansvar at returnere et Customer objekt ud fra parametrene i metodens header.
        Metoden bruges i BookingController klassen hvor parametrene indtastet i et view når en ny kunde
        skal oprettes i forbindelse med en ny booking.

        getLast() kaldes for at sætte id på objektet for at sørge for at id vil være det næste i følge databasen.
     */
    public Customer generateCustomerFromCustomerForm(String firstName, String lastName, int phone, String mail, int zipCode, String city, String address) {
        Customer customerToBeCreated = new Customer(firstName, lastName, phone, mail, zipCode, city, address);
        add(customerToBeCreated);

        customerToBeCreated.setId(getLast().getId());

        return customerToBeCreated;
    }

    @Override
    public void add(Customer customer) {
        try {
            RepositoryManager.getInstance().getCustomerRepository().create(customer);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer get(int id) {
        try {
            return RepositoryManager.getInstance().getCustomerRepository().read(id);
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(Customer customer) {
        try {
            RepositoryManager.getInstance().getCustomerRepository().update(customer);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        try {
            RepositoryManager.getInstance().getCustomerRepository().delete(id);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> getAll() {
        try {
            return RepositoryManager.getInstance().getCustomerRepository().readAll();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
        Metoden returnerer et Customer objekt som er lavet ud fra det sidste i row i databasen.
        Med andre ord den senest oprettede Customer.
        Dette bruges i BookingController klassen når man opretter en ny customer i forbindelse med en booking.
     */
    @Override
    public Customer getLast() {
        try {
            return RepositoryManager.getInstance().getCustomerRepository().readLast();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
