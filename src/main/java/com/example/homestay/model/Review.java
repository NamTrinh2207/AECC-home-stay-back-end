package com.example.homestay.model;

import javax.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Homes homes;
    @OneToOne
    private Users users;
    private int rating;
    @Lob
    private String comment;


}
