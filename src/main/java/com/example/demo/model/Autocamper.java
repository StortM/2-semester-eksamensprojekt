package com.example.demo.model;

public class Autocamper {
    private int id;
    private int year;
    private int priceDay;
    private int beds;
    private int seats;
    private String description;
    private String brand;
    private String model;

    // Default constructor så vi kan oprette objekter til nye autocampere
    public Autocamper(){

    }

    public Autocamper(int id, int year, int priceDay, int beds, int seats, String description, String brand, String model) {
        this.id = id;
        this.year = year;
        this.priceDay = priceDay;
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

    public int getPriceDay() {
        return priceDay;
    }

    public void setPriceDay(int priceDay) {
        this.priceDay = priceDay;
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
        return "Autocamper{" +
                "id=" + id +
                ", year=" + year +
                ", price_day=" + priceDay +
                ", beds=" + beds +
                ", seats=" + seats +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
