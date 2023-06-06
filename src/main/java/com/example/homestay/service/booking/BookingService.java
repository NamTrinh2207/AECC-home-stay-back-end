package com.example.homestay.service.booking;

import com.example.homestay.model.Booking;
import com.example.homestay.model.DTO.IGetMostRentedBooking;
import com.example.homestay.model.Homes;
import com.example.homestay.repository.IBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService {
    @Autowired
    private IBookingRepository bookingRepository;

    @Override
    public Iterable<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void remove(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Iterable<IGetMostRentedBooking> getMostRentedBooking() {
        return bookingRepository.getMostRentedBooking();
    }

    @Override
    public Page<Booking> findByUserId(Long id, Pageable pageable) {
        return bookingRepository.findByUsers_Id(id, pageable);
    }

    @Override
    public Iterable<Booking> getAllBookingsIdByHomeId(Long id) {
        return bookingRepository.getAllBookingsIdByHomeId(id);
    }

    @Override
    public Page<Booking> findBookingsByUsersId(Long id, boolean status, boolean done, Pageable pageable) {
        return bookingRepository.findByUsers_IdAndStatusBAndDone(id, status, done, pageable);
    }

    @Override
    public List<Booking> getAllBookingByUserIdAndStatusAndDone(Long id) {
        return bookingRepository.getAllBookingByUserIdAndStatusAndDone(id);
    }

    @Override
    public List<Booking> getUncheckedBooking(Long id) {
        return bookingRepository.getUncheckedBooking(id);
    }

    @Override
    public List<Booking> getCheckedBooking(Long id) {
        return bookingRepository.getCheckedBooking(id);
    }

    @Override
    public List<Booking> getCancelRequest(Long id) {
        return bookingRepository.getCancelRequest(id);
    }

    @Override
    public List<Booking> getAllBookingByUserIdAndStatusFalse(Long id) {
        return bookingRepository.getAllBookingByUserIdAndStatusFalse(id);
    }

    @Override
    public List<Booking> getBookingByUserIddAndStatusTrue(Long id) {
        return bookingRepository.getBookingByUserIddAndStatusTrue(id);
    }

    @Override
    public Optional<Booking> getFirstByUsersIdAndHomeId(Long user_id, Long home_id) {
        return bookingRepository.getFirstByUsersIdAndHomeId(user_id, home_id);
    }
}