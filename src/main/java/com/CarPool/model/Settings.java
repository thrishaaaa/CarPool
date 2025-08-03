package com.CarPool.model;

import jakarta.persistence.*;

@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double fuelCostPerKm;
    private String bookingDeadline; // can store "21:00" for 9PM or convert to time

    public Long getId() {
        return id;
    }

    public double getFuelCostPerKm() {
        return fuelCostPerKm;
    }

    public String getBookingDeadline() {
        return bookingDeadline;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFuelCostPerKm(double fuelCostPerKm) {
        this.fuelCostPerKm = fuelCostPerKm;
    }

    public void setBookingDeadline(String bookingDeadline) {
        this.bookingDeadline = bookingDeadline;
    }
    // Getters & Setters
}
