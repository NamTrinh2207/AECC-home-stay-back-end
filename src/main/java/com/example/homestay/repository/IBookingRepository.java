package com.example.homestay.repository;

import com.example.homestay.model.Booking;
import com.example.homestay.model.DTO.IGetMostRentedBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {
    @Query(nativeQuery = true, value = "SELECT h.id, h.address as address, h.bedroom, h.bathroom, h.name as name, h.price_by_day as pricebyday, ht.name as hometype, u.name as username,  COUNT( DISTINCT b.id) AS bookingcount,\n" +
            "                 min(hi.image) AS images\n" +
            "            FROM homes h\n" +
            "                     join booking b on h.id = b.home_id\n" +
            "                    join home_type ht on h.home_type_id = ht.id\n" +
            "                    join users u on h.user_id = u.id\n" +
            "                     join homes_image hi on h.id = hi.homes_id\n" +
            "            GROUP BY h.id, h.address, h.name, h.price_by_day, u.name ,ht.name, h.bedroom, h.bathroom\n" +
            "            ORDER BY bookingcount DESC\n" +
            "            LIMIT 4;")
    Iterable<IGetMostRentedBooking> getMostRentedBooking();

    @Query(nativeQuery = true, value = "select\n" +
            "    h.name,\n" +
            "    b.checkin,\n" +
            "       b.checkout,\n" +
            "       b.is_paid,\n" +
            "       b.total_price,\n" +
            "       b.home_id,\n" +
            "       b.user_id `customer_id`\n" +
            "FROM users u\n" +
            "INNER JOIN homes h ON u.id = h.user_id\n" +
            "INNER JOIN booking b ON h.id = b.home_id WHERE u.id = :id")
    List<Object> getBookingByOwner(@Param("id") Long id);
}
