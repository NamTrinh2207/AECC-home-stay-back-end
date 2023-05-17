package com.example.homestay.repository;

import com.example.homestay.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {
}
