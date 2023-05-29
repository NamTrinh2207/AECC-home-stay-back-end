package com.example.homestay.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.homestay.model.Homes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HomeRepository extends JpaRepository<Homes, Long> {
    Page<Homes> findAll(Pageable pageable);

    Page<Homes> findByUsers_Id(Long userId, Pageable pageable);

    @Query(value = "SELECT DISTINCT h.id,\n" +
            "                h.address,\n" +
            "                h.bathroom,\n" +
            "                h.bedroom,\n" +
            "                h.name as nameHome,\n" +
            "                h.price_by_day,\n" +
            "                h.status,\n" +
            "                b.checkin,\n" +
            "                b.checkout,\n" +
            "                MAX(hi.image) AS image,\n" +
            "                u.name as username,\n" +
            "                ht.name as homeTypeName\n" +
            "FROM homes h\n" +
            "        LEFT JOIN home_type ht on ht.id = h.home_type_id\n" +
            "        INNER JOIN booking b ON h.id = b.home_id\n" +
            "        INNER JOIN homes_image hi ON h.id = hi.homes_id\n" +
            "        INNER JOIN users u ON u.id = h.user_id\n" +
            "WHERE (:bedroom IS NULL OR h.bedroom = :bedroom)\n" +
            "  AND (:bathroom IS NULL OR h.bathroom = :bathroom)\n" +
            "  AND (:address IS NULL OR h.address LIKE CONCAT('%', :address, '%'))\n" +
            "  AND ((:start_date IS NULL OR :end_date IS NULL) OR (b.checkin IS NULL OR b.checkout IS NULL)\n" +
            "    OR (b.checkin >= :start_date AND b.checkout <= :end_date))\n" +
            "  AND ((:min_price IS NULL OR :max_price IS NULL)\n" +
            "    OR (h.price_by_day BETWEEN :min_price AND :max_price))\n" +
            "GROUP BY h.id, h.address, h.bathroom, h.bedroom, h.name, h.price_by_day, h.status, b.checkin,\n" +
            "         b.checkout, u.name,ht.name;",
            nativeQuery = true)
    List<Object> searchHomes(@Param("bedroom") Integer bedroom,
                             @Param("bathroom") Integer bathroom,
                             @Param("address") String address,
                             @Param("start_date") LocalDate startDate,
                             @Param("end_date") LocalDate endDate,
                             @Param("min_price") BigDecimal minPrice,
                             @Param("max_price") BigDecimal maxPrice);


    @Query(
            nativeQuery = true,
            value = "UPDATE homes\n" +
                    "SET status = 3\n" +
                    "WHERE id = :id;")
    Optional<Homes> updateStatusAfterBooking(@Param("id") Long id);
}
