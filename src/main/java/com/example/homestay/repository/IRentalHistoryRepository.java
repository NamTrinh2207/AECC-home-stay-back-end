package com.example.homestay.repository;

import com.example.homestay.model.DTO.RentalHistoryHotel;
import com.example.homestay.model.Homes;
import com.example.homestay.model.RentalHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRentalHistoryRepository extends JpaRepository<RentalHistory, Long> {

    @Query(nativeQuery = true,value = "SELECT b.checkin, b.checkout, b.total_price as totalPrice, h.address, h.bathroom, h.bedroom, h.description, h.name, h.price_by_day as priceByDay, hi.image\n" +
            "            FROM rental_history r join booking b on b.id = r.booking_id join homes h on h.id = b.home_id join homes_image hi on h.id = hi.homes_id\n" +
            "            group by b.checkin, b.checkout, b.total_price, h.address, h.bathroom, h.bedroom, h.description, h.name, h.price_by_day, hi.image")
    Page<RentalHistoryHotel> listHistory(Pageable pageable);
}
