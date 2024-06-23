package com.example.pharmacymanagement.Models;

public class Purchase {
    private String drugId;
    private int quantity;
    private double totalAmount;
    private String purchaseDate;

    public Purchase(String drugId, int quantity, double totalAmount, String purchaseDate) {
        this.drugId = drugId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.purchaseDate = purchaseDate;
    }

    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
