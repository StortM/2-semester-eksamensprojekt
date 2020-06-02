package com.example.demo.repository;

import com.example.demo.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl implements ICustomerRepository {
    private Connection conn;

    public CustomerRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create(Customer customer) {
        try {
            PreparedStatement statementToInsert = conn.prepareStatement("INSERT INTO customers " + "(first_name, last_name, phone, mail, zip_code, city, address)" + " VALUES (?, ?, ?, ?, ?, ?, ?)");
            statementToInsert.setString(1, customer.getFirstName());
            statementToInsert.setString(2, customer.getLastName());
            statementToInsert.setInt(3, customer.getPhone());
            statementToInsert.setString(4, customer.getMail());
            statementToInsert.setInt(5, customer.getZipCode());
            statementToInsert.setString(6, customer.getCity());
            statementToInsert.setString(7, customer.getAddress());

            statementToInsert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer read(int id) {
        Customer customerToReturn = new Customer();

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
    public void update(Customer customer) {

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

            statementToExecute.setString(1, customer.getFirstName());
            statementToExecute.setString(2, customer.getLastName());
            statementToExecute.setInt(3, customer.getPhone());
            statementToExecute.setString(4, customer.getMail());
            statementToExecute.setInt(5, customer.getZipCode());
            statementToExecute.setString(6, customer.getCity());
            statementToExecute.setString(7, customer.getAddress());

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
    public List<Customer> readAll() {
        List<Customer> customerList = new ArrayList<>();
        try {
            PreparedStatement statementToQuery = conn.prepareStatement("SELECT * FROM customers");
            ResultSet rs = statementToQuery.executeQuery();

            while(rs.next()){
                Customer customer = new Customer();
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

    /* Sætter resultset til at være TYPE_SCROLL_INSENSITIVE da resultset pr. default er TYPE_FORWARD_ONLY
        som kun understøtter at cursoren bevæger sig 1 row ad gangen, hvilket ikke virker med last() metoden
     */
    public Customer readLast() {
        Customer customerToReturn = new Customer();

        try {
            PreparedStatement statementToQuery = conn.prepareStatement("SELECT * FROM customers ORDER BY id", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statementToQuery.executeQuery();

            rs.last();

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
}
