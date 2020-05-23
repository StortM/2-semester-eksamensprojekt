package com.example.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO implements CRUDRepository<BookingDTO> {
    private Connection conn;

    public BookingDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(BookingDTO bookingDTO) {

    }

    public BookingDTO read(int id) {
        return null;
    }

    public void update(BookingDTO bookingDTO) {

    }

    public void delete(int id) {

    }

    public List<BookingDTO> readAll() {
        List<BookingDTO> bookingsList = new ArrayList<>();

        try {
            PreparedStatement statementToQuery = conn.prepareStatement("SELECT * FROM bookings");
            ResultSet rs = statementToQuery.executeQuery();

            while(rs.next()) {
                BookingDTO tempBooking = new BookingDTO();
                tempBooking.setId(rs.getInt(1));
                tempBooking.setAutocamperId(rs.getInt(2));
                tempBooking.setCustomerId(rs.getInt(3));
                tempBooking.setPeriodStart(rs.getDate(4).toLocalDate());
                tempBooking.setPeriodEnd(rs.getDate(5).toLocalDate());
                tempBooking.setDropOff(rs.getString(6));
                tempBooking.setPickUp(rs.getString(7));
                tempBooking.setPriceTotal(rs.getInt(8));

                bookingsList.add(tempBooking);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return bookingsList;
    }
}
