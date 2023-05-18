package com.example.homestay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "homeType")
public class HomeType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "homeType")
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Set<Homes> homes;

    public HomeType() {
    }

    public HomeType(String name) {
        this.name = name;
    }

    public HomeType(Set<Homes> homes) {
        this.homes = homes;
    }

    public HomeType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public HomeType(Long id, Set<Homes> homes) {
        this.id = id;
        this.homes = homes;
    }

    public HomeType(String name, Set<Homes> homes) {
        this.name = name;
        this.homes = homes;
    }

    public HomeType(Long id, String name, Set<Homes> homes) {
        this.id = id;
        this.name = name;
        this.homes = homes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Homes> getHomes() {
        return homes;
    }

    public void setHomes(Set<Homes> homes) {
        this.homes = homes;
    }
}