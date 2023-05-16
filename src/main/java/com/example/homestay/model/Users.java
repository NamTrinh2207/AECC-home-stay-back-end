package com.example.homestay.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class Users implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String username;
    private String password;
    @Lob
    private String avatar;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private boolean isVerified;
    private String verificationToken;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_role")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Roles> roles;
    @OneToMany(mappedBy = "users")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "users")
    private List<Homes> homes;


    public Users() {
    }

    public Users(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isVerified = false;
        this.verificationToken = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        if (avatar == null)
            avatar = "https://banner2.cleanpng.com/20190504/irf/kisspng-computer-icons-portable-network-graphics-user-prof-avatar-people-social-user-profile-icon-5ccd663d0e0c99.6819973515569649250576.jpg";
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Homes> getHomes() {
        return homes;
    }

    public void setHomes(List<Homes> homes) {
        this.homes = homes;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }
}
