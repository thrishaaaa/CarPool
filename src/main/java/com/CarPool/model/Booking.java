package com.CarPool.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    private LocalDate rideDate;
    private String rideType;  // "pickup" or "drop"
    private String status;    // "booked" or "cancelled"

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Provider getProvider() {
        return provider;
    }

    public LocalDate getRideDate() {
        return rideDate;
    }

    public String getRideType() {
        return rideType;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public void setRideDate(LocalDate rideDate) {
        this.rideDate = rideDate;
    }

    public void setRideType(String rideType) {
        this.rideType = rideType;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // Getters & Setters
}
