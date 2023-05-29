package com.example.homestay.model.DTO;

import java.util.Date;

public interface  RentalHistoryHotel {
    Date getCheckin();
    Date getCheckout();
    Long getTotalPrice();
    String getAddress();
    int getBathroom();
    int getBedroom();
    String getDescription();
    String getName();
    Long getPriceByDay();
    String getImage();
}
