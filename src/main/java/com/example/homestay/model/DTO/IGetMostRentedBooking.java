package com.example.homestay.model.DTO;

import java.util.List;

public interface IGetMostRentedBooking {
    int getId();
    String getAddress();
    String getName();
    Long getPriceByDay();
    String getHomeType();
    String getUsername();
    int getBookingCount();
    List<String> getImages();
}
