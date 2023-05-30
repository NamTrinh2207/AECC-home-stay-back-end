package com.example.homestay.service.booking;

import com.example.homestay.model.Booking;
import com.example.homestay.model.DTO.IGetMostRentedBooking;

import com.example.homestay.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IBookingService extends IGeneralService<Booking> {
    Iterable<IGetMostRentedBooking> getMostRentedBooking();
    Page<Booking> findByUserId(Long id, Pageable pageable);

    Page<Booking> getBookingByOwnerAndIsPaid(Long id, boolean isPaid, Pageable pageable);

    Iterable<Booking> getAllBookingsIdByHomeId(Long id);


}
