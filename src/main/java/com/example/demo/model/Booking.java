package com.example.demo.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/*
    POJO af booking
 */

public class Booking {
    private int id;
    private int autocamperId;
    private int customerId;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // needed for input field on html pages (in order to serve the right format)
    private LocalDate periodStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // needed for input field on html pages (in order to serve the right format)
    private LocalDate periodEnd;
    private String dropOff;
    private String pickUp;
    private int priceTotal;

    // Default constructor så vi kan oprette objekter til nye bookinger
    public Booking() {

    }

    public Booking(int id, int autocamperId, int customerId, LocalDate periodStart, LocalDate periodEnd, String dropOff, String pickUp, int priceTotal) {
        this.id = id;
        this.autocamperId = autocamperId;
        this.customerId = customerId;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.dropOff = dropOff;
        this.pickUp = pickUp;
        this.priceTotal = priceTotal;
    }

    // Constructor uden id da vi lader DB håndterer tildeling af id til hvert entry
    public Booking(int autocamperId, int customerId, LocalDate periodStart, LocalDate periodEnd, String dropOff, String pickUp, int priceTotal) {
        this.autocamperId = autocamperId;
        this.customerId = customerId;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.dropOff = dropOff;
        this.pickUp = pickUp;
        this.priceTotal = priceTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAutocamperId() {
        return autocamperId;
    }

    public void setAutocamperId(int autocamperId) {
        this.autocamperId = autocamperId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDate periodStart) {

        this.periodStart = periodStart;
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
    }

    public String getDropOff() {
        return dropOff;
    }

    public void setDropOff(String dropOff) {
        this.dropOff = dropOff;
    }

    public String getPickUp() {
        return pickUp;
    }

    public void setPickUp(String pickUp) {
        this.pickUp = pickUp;
    }

    public int getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(int priceTotal) {
        this.priceTotal = priceTotal;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", autocamperId=" + autocamperId +
                ", customerId=" + customerId +
                ", periodStart=" + periodStart +
                ", periodEnd=" + periodEnd +
                ", dropOff='" + dropOff + '\'' +
                ", pickUp='" + pickUp + '\'' +
                ", priceTotal=" + priceTotal +
                '}';
    }
}
