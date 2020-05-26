package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.repository.RepositoryManager;

import java.sql.SQLException;
import java.util.List;

public class CustomerServiceImpl implements ICustomerService {

    public CustomerServiceImpl() {

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
