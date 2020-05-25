package com.example.demo.repository;

import com.example.demo.model.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookingRepositoryImpl implements IBookingRepository {
    private Connection conn;

    public BookingRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create(Booking booking) {
        try{
            PreparedStatement statementToInsert = conn.prepareStatement("INSERT INTO bookings (autocamper_id, customer_id, period_start, period_end, dropoff, pickup, price_total) " +
                    " VALUES (?,?,?,?,?,?,?)");
            statementToInsert.setInt(1, booking.getAutocamperId());
            statementToInsert.setInt(2, booking.getCustomerId());
            statementToInsert.setDate(3, Date.valueOf(booking.getPeriodStart()));
            statementToInsert.setDate(4, Date.valueOf(booking.getPeriodEnd()));
            statementToInsert.setString(5, booking.getDropOff());
            statementToInsert.setString(6, booking.getPickUp());
            statementToInsert.setInt(7, booking.getPriceTotal());

            statementToInsert.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Booking read(int id) {
        Booking bookingToReturn = new Booking();

        try{
            PreparedStatement statementToQuery = conn.prepareStatement("SELECT * FROM bookings WHERE id=?");
            statementToQuery.setInt(1,id);
            ResultSet rs = statementToQuery.executeQuery();

            bookingToReturn.setId(rs.getInt(1));
            bookingToReturn.setAutocamperId(rs.getInt(2));
            bookingToReturn.setCustomerId(rs.getInt(3));
            bookingToReturn.setPeriodStart(rs.getDate(4).toLocalDate());
            bookingToReturn.setPeriodEnd(rs.getDate(5).toLocalDate());
            bookingToReturn.setDropOff(rs.getString(6));
            bookingToReturn.setPickUp(rs.getString(7));
            bookingToReturn.setPriceTotal(rs.getInt(8));

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return bookingToReturn;
    }

    @Override
    public void update(Booking booking) {
            try {
                PreparedStatement statementToExecute = conn.prepareStatement("UPDATE bookings SET " +
                        "autocamper_id = ?, " +
                        "customer_id = ?, " +
                        "period_start = ?, " +
                        "period_end = ?, " +
                        "dropoff = ?," +
                        "pickup = ?," +
                        "price_total = ?" +
                        "WHERE id = ?");

                statementToExecute.setInt(1, booking.getAutocamperId());
                statementToExecute.setInt(2, booking.getCustomerId());
                statementToExecute.setDate(3,Date.valueOf(booking.getPeriodStart()));
                statementToExecute.setDate(4,Date.valueOf(booking.getPeriodEnd()));
                statementToExecute.setString(5, booking.getDropOff());
                statementToExecute.setString(6, booking.getPickUp());
                statementToExecute.setInt(7, booking.getPriceTotal());

                statementToExecute.executeUpdate();

        }
            catch (SQLException e){
                e.printStackTrace();
            }
    }

    @Override
    public void delete(int id) {
        try{
            PreparedStatement statementToExecute = conn.prepareStatement("DELETE FROM bookings WHERE id=?");
            statementToExecute.setInt(1,id);
            statementToExecute.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Booking> readAll() {
        List<Booking> bookingsList = new ArrayList<>();

        try {
            PreparedStatement statementToQuery = conn.prepareStatement("SELECT * FROM bookings");
            ResultSet rs = statementToQuery.executeQuery();

            while(rs.next()) {
                Booking tempBooking = new Booking();
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
    public Booking readLast() {
        Booking bookingToReturn = new Booking();

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
