package com.CarPool.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;
    private String pickupPoint;

    @ManyToOne
    @JoinColumn(name = "college_id")
    private College college;

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setPickupPoint(String pickupPoint) { this.pickupPoint = pickupPoint; }
    public void setCollege(College college) { this.college = college; }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getPickupPoint() { return pickupPoint; }
    public College getCollege() { return college; }
}
