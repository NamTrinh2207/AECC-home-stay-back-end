package com.example.homestay.service.booking;

import com.example.homestay.model.Booking;
import com.example.homestay.model.DTO.IGetMostRentedBooking;
import com.example.homestay.model.Homes;
import com.example.homestay.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBookingService extends IGeneralService<Booking> {
    Iterable<IGetMostRentedBooking> getMostRentedBooking();
    List<Booking> getBookingByOwner(Long id);
}
