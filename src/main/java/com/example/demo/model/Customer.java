package com.example.demo.model;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private int phone;
    private String mail;
    private int zipCode;
    private String city;
    private String address;

    // Default constructor så vi kan oprette objekter til nye kunder
   public Customer() {

    }

    public Customer(int id, String firstName, String lastName, int phone, String mail, int zipCode, String city, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.mail = mail;
        this.zipCode = zipCode;
        this.city = city;
        this.address = address;
    }

    // Constructor uden id da vi lader DB håndterer tildeling af id til hvert entry
    public Customer(String firstName, String lastName, int phone, String mail, int zipCode, String city, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.mail = mail;
        this.zipCode = zipCode;
        this.city = city;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
