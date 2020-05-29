package com.example.demo.service;

import com.example.demo.model.Customer;

public interface ICustomerService extends IService<Customer>{

    Customer getLast();
    Customer generateCustomerFromCustomerForm(String firstName, String lastName, int phone, String mail, int zipCode, String city, String address);
}