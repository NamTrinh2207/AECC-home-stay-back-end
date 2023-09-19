package com.example.homestay.model.DTO;

import java.util.Set;

public class SearchResultDTO {
    private Long id;
    private String address;
    private int bedroom;
    private int bathroom;
    private String homeName;
    private Long priceByDay;
    private int status;
    private String image;
    private String username;
    private String homeType;

    public SearchResultDTO(Long id, String address, int bedroom, int bathroom, String name, Long priceByDay, int status, Set<String> image, String username, String homeType) {
        this.id = id;
        this.address = address;
        this.homeName = name;
        this.status = status;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.priceByDay = priceByDay;
        this.image = image.toString();
        this.username = username;
        this.homeType = homeType;
    }

    public SearchResultDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public int getBedroom() {
        return bedroom;
    }

    public int getBathroom() {
        return bathroom;
    }

    public String getHomeName() {
        return homeName;
    }

    public Long getPriceByDay() {
        return priceByDay;
    }

    public int getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public String getUsername() {
        return username;
    }

    public String getHomeType() {
        return homeType;
    }
}
