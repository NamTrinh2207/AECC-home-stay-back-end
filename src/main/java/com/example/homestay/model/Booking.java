package com.example.homestay.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private LocalDate checkin;
    private LocalDate checkout;
    private Long totalPrice;
    private boolean isPaid;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @OneToOne
    @JoinColumn(name = "home_id")
    private Homes homes;
    private boolean statusB;
    private boolean isCheckinB;
    private boolean isCheckoutB;
    private boolean isDone;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Booking() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Homes getHomes() {
        return homes;
    }

    public void setHomes(Homes homes) {
        this.homes = homes;
    }

    public boolean isStatusB() {
        return statusB;
    }

    public void setStatusB(boolean statusB) {
        this.statusB = statusB;
    }

    public boolean isCheckinB() {
        return isCheckinB;
    }

    public void setCheckinB(boolean checkinB) {
        isCheckinB = checkinB;
    }

    public boolean isCheckoutB() {
        return isCheckoutB;
    }

    public void setCheckoutB(boolean checkoutB) {
        isCheckoutB = checkoutB;
    }
}