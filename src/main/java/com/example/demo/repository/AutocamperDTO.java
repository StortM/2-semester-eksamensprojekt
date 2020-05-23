package com.example.demo.repository;

public class AutocamperDTO{
    private int id;
    private int year;
    private int price_day;
    private int beds;
    private int seats;
    private String description;
    private String brand;
    private String model;

    public AutocamperDTO(){

    }

    public AutocamperDTO(int id, int year, int price_day, int beds, int seats, String description, String brand, String model) {
        this.id = id;
        this.year = year;
        this.price_day = price_day;
        this.beds = beds;
        this.seats = seats;
        this.description = description;
        this.brand = brand;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice_day() {
        return price_day;
    }

    public void setPrice_day(int price_day) {
        this.price_day = price_day;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "AutocamperDTO{" +
                "id=" + id +
                ", year=" + year +
                ", price_day=" + price_day +
                ", beds=" + beds +
                ", seats=" + seats +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
