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
public class BookingService implements IBookingService{
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
    public Page<Booking> getBookingByOwner(Long id, Pageable pageable) {
        return bookingRepository.getBookingByOwner(id, pageable);
    }

    @Override
    public Iterable<Booking> getAllBookingsIdByHomeId(Long id) {
        return bookingRepository.getAllBookingsIdByHomeId(id);
    }
}
