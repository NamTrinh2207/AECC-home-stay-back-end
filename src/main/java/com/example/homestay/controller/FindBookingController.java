package com.example.homestay.controller;

import com.example.homestay.model.DTO.IGetMostRentedBooking;
import com.example.homestay.service.booking.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/homes")
public class FindBookingController {
    @Autowired
    private IBookingService iBookingService;
    @GetMapping("/findBooking")
    public ResponseEntity<Iterable<IGetMostRentedBooking>> findFiveMostBooking() {
        Iterable<IGetMostRentedBooking> iGetMostRentedBookings = iBookingService.getMostRentedBooking();
        return new ResponseEntity<>(iGetMostRentedBookings, HttpStatus.OK);
    }

}
