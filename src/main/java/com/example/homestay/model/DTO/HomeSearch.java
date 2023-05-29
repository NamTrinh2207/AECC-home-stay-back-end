package com.example.homestay.model.DTO;

import java.sql.Date;

public interface HomeSearch {
    Long getId();

    String getAddress();

    Long getBathroom();

    Long getBedroom();

    String getHomeName();

    Long getPriceByDay();

    Long getStatus();

    Date getCheckin();

    Date getCheckout();

    String getImage();

    String getUsername();

    String getHomeType();
}