package com.example.homestay.service.booking;

import com.example.homestay.model.Booking;
import com.example.homestay.model.DTO.IGetMostRentedBooking;

import com.example.homestay.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IBookingService extends IGeneralService<Booking> {
    Iterable<IGetMostRentedBooking> getMostRentedBooking();
    Page<Booking> findByUserId(Long id, Pageable pageable);

    Iterable<Booking> getAllBookingsIdByHomeId(Long id);

    Page<Booking> findBookingsByUsersId(Long id,boolean status, boolean done, Pageable pageable);


    Page<Booking> getAllBookingByUserIdAndStatusAndDone(Long id, Pageable pageable);

    List<Booking> getUncheckedBooking(Long id);

    List<Booking> getCheckedBooking(Long id);

    List<Booking> getCancelRequest(Long id);

}
