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

    @Override
    public void create(BookingDTO bookingDTO) {

    }

    @Override
    public BookingDTO read(int id) {
        return null;
    }

    @Override
    public void update(BookingDTO bookingDTO) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
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

    /* Sætter resultset til at være TYPE_SCROLL_INSENSITIVE da resultset pr. default er TYPE_FORWARD_ONLY
    som kun understøtter at cursoren bevæger sig 1 row ad gangen, hvilket ikke virker med last() metoden
 */
    public BookingDTO readLast() {
        BookingDTO bookingToReturn = new BookingDTO();

        try {
            PreparedStatement statementToQuery = conn.prepareStatement("SELECT * FROM bookings ORDER BY id", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statementToQuery.executeQuery();

            rs.last();

            bookingToReturn.setId(rs.getInt(1));
            bookingToReturn.setAutocamperId(rs.getInt(2));
            bookingToReturn.setCustomerId(rs.getInt(3));
            bookingToReturn.setPeriodStart(LocalDate.parse(rs.getDate(4).toString()));
            bookingToReturn.setPeriodEnd(LocalDate.parse(rs.getDate(5).toString()));
            bookingToReturn.setDropOff(rs.getString(6));
            bookingToReturn.setPickUp(rs.getString(7));
            bookingToReturn.setPriceTotal(rs.getInt(8));

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return bookingToReturn;
    }
}
