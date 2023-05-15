package com.example.homestay.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "homeType")
public class HomeType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "homeType")
    private List<Homes> homes;

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

    public List<Homes> getHomes() {
        return homes;
    }

    public void setHomes(List<Homes> homes) {
        this.homes = homes;
    }
}
