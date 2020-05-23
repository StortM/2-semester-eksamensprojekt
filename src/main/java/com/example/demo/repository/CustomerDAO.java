package com.example.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAO implements CRUDRepository<CustomerDTO>{
    private Connection conn;

    public CustomerDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(CustomerDTO customerDTO) {
        try {
            PreparedStatement statementToInsert = conn.prepareStatement("INSERT INTO customers VALUES (?, ?, ?, ?, ?, ?, ?)");
            // statementToInsert.setString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CustomerDTO read(int id) {
        return null;
    }

    public void update(CustomerDTO customerDTO) {

    }

    public void delete(int id) {

    }

    public List<CustomerDTO> readAll() {
        return null;
    }
}
