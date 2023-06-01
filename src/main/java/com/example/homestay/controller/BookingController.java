package com.example.homestay.controller;

import com.example.homestay.model.Booking;
import com.example.homestay.service.booking.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")

@RequestMapping("/customer/bookings")

public class BookingController {
    private final IBookingService bookingService;

    @Autowired
    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<Iterable<Booking>> listBookings() {
        Iterable<Booking> bookings = bookingService.findAll();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return new ResponseEntity<>(bookingService.save(booking), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Booking> editBooking(@PathVariable Long id, @RequestBody Booking booking) {
        Optional<Booking> bookingOptional = bookingService.findById(id);
        if (bookingOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            booking.setId(bookingOptional.get().getId());
            return new ResponseEntity<>(bookingService.save(booking), HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        bookingService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Booking> viewBooking(@PathVariable Long id) {
        Optional<Booking> bookingOptional = bookingService.findById(id);
        return bookingOptional.map(booking -> new ResponseEntity<>(booking, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/setStatus/{id}")
    public ResponseEntity<Booking> setStatusBooking(@PathVariable Long id) {
        Optional<Booking> bookingOptional = bookingService.findById(id);
        if (bookingOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            bookingOptional.get().setPaid(true);
            return new ResponseEntity<>(bookingService.save(bookingOptional.get()), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<Booking>> getBookingByUserId(@PathVariable Long id, @RequestParam(defaultValue = "0") int page) {

        PageRequest pages = PageRequest.of(page, 5);
        Page<Booking> bookings = bookingService.findByUserId(id, pages);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/home/{id}")
    public ResponseEntity<Iterable<Booking>> getBookingIdByHomeId(@PathVariable Long id) {
        Iterable<Booking> getAllBookingByHomeId = bookingService.getAllBookingsIdByHomeId(id);
        return new ResponseEntity<>(getAllBookingByHomeId, HttpStatus.OK);
    }
    @GetMapping("/status/{id}")
    public ResponseEntity<Page<Booking>> getBookingByUserIdAndStatusAndDone(@PathVariable Long id, @RequestParam(defaultValue = "0") int page) {
        PageRequest pages = PageRequest.of(page, 5);
        Page<Booking> bookings = bookingService.getAllBookingByUserIdAndStatusAndDone(id, pages);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}

