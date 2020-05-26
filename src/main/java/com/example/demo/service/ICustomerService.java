package com.example.demo.service;

import com.example.demo.model.Customer;

public interface ICustomerService extends IService<Customer>{

    Customer getLast();
}
