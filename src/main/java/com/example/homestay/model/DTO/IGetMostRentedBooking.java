package com.example.homestay.model.DTO;

import java.util.List;

public interface IGetMostRentedBooking {
    int getId();

    String getAddress();

    Double getRating();

    int getBedroom();

    int getBathroom();

    int getStatus();

    String getName();

    Long getPriceByDay();

    String getHomeType();

    String getUsername();

    int getBookingCount();

    List<String> getImages();
}