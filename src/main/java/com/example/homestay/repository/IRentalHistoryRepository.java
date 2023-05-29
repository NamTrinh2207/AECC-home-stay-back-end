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

    @Query(nativeQuery = true,value = "SELECT b.id,\n" +
            "    b.checkin,\n" +
            "       b.checkout,\n" +
            "       b.total_price  as totalPrice,\n" +
            "       h.address,\n" +
            "       h.bathroom,\n" +
            "       h.bedroom,\n" +
            "       h.description,\n" +
            "       h.name,\n" +
            "       h.price_by_day as priceByDay,\n" +
            "       MAX(hi.image)  as image\n" +
            "FROM rental_history r\n" +
            "         join booking b on b.id = r.booking_id\n" +
            "         join homes h on h.id = b.home_id\n" +
            "         join homes_image hi on h.id = hi.homes_id\n" +
            "group by b.id")
    Page<RentalHistoryHotel> listHistory(Pageable pageable);
}
