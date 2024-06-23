package com.example.pharmacymanagement.Models;

public class Supplier {
    private String supplierId;
    private String name;
    private String location;
    private String contact;

    public Supplier(String supplierId, String name, String location, String contact) {
        this.supplierId = supplierId;
        this.name = name;
        this.location = location;
        this.contact = contact;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setAddress(String text) {

    }
}
