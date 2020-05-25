package com.example.demo.repository;

import com.example.demo.model.Customer;

public interface ICustomerRepository extends ICRUDRepository<Customer> {
    Customer readLast();
}
