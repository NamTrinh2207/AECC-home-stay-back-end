package com.example.homestay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "homeType")
public class HomeType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "homeType",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Homes> homes;

    public HomeType() {
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