package com.example.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements CRUDRepository<CustomerDTO>{
    private Connection conn;

    public CustomerDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create(CustomerDTO customerDTO) {
        try {
            PreparedStatement statementToInsert = conn.prepareStatement("INSERT INTO customers VALUES (?, ?, ?, ?, ?, ?, ?)");
            statementToInsert.setString(1, customerDTO.getFirstName());
            statementToInsert.setString(2, customerDTO.getLastName());
            statementToInsert.setInt(3, customerDTO.getPhone());
            statementToInsert.setString(4, customerDTO.getMail());
            statementToInsert.setInt(5, customerDTO.getZipCode());
            statementToInsert.setString(6, customerDTO.getCity());
            statementToInsert.setString(7, customerDTO.getAddress());

            statementToInsert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CustomerDTO read(int id) {
        CustomerDTO customerToReturn = new CustomerDTO();

        try {
            PreparedStatement statementToQuery = conn.prepareStatement("SELECT * FROM customers WHERE id=?");
            statementToQuery.setInt(1, id);
            ResultSet rs = statementToQuery.executeQuery();

            customerToReturn.setId(rs.getInt(1));
            customerToReturn.setFirstName(rs.getString(2));
            customerToReturn.setLastName(rs.getString(3));
            customerToReturn.setPhone(rs.getInt(4));
            customerToReturn.setMail(rs.getString(5));
            customerToReturn.setZipCode(rs.getInt(6));
            customerToReturn.setCity(rs.getString(7));
            customerToReturn.setAddress(rs.getString(8));

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return customerToReturn;
    }

    @Override
    public void update(CustomerDTO customerDTO) {

        try {
            PreparedStatement statementToExecute = conn.prepareStatement("UPDATE customers SET " +
                    "first_name = ?, " +
                    "last_name = ?, " +
                    "phone = ?, " +
                    "mail = ?, " +
                    "zip_code = ?," +
                    "city = ?," +
                    "address = ?" +
                    "WHERE id = ?");

            statementToExecute.setString(1,customerDTO.getFirstName());
            statementToExecute.setString(2,customerDTO.getLastName());
            statementToExecute.setInt(3,customerDTO.getPhone());
            statementToExecute.setString(4,customerDTO.getMail());
            statementToExecute.setInt(5,customerDTO.getZipCode());
            statementToExecute.setString(6,customerDTO.getCity());
            statementToExecute.setString(7,customerDTO.getAddress());

            statementToExecute.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {

        try {
            PreparedStatement statementToExecute = conn.prepareStatement("DELETE FROM customers WHERE id = ?");
            statementToExecute.setInt(1,id);
            statementToExecute.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CustomerDTO> readAll() {
        List<CustomerDTO> customerList = new ArrayList<>();
        try {
            PreparedStatement statementToQuery = conn.prepareStatement("SELECT * FROM customers");
            ResultSet rs = statementToQuery.executeQuery();

            while(rs.next()){
                CustomerDTO customer = new CustomerDTO();
                customer.setId(rs.getInt(1));
                customer.setFirstName(rs.getString(2));
                customer.setLastName(rs.getString(3));
                customer.setPhone(rs.getInt(4));
                customer.setMail(rs.getString(5));
                customer.setZipCode(rs.getInt(6));
                customer.setCity(rs.getString(7));
                customer.setAddress(rs.getString(8));

                customerList.add(customer);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }
}
