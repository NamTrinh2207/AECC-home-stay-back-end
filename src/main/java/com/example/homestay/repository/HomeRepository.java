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

@Repository
public interface HomeRepository extends JpaRepository<Homes, Long> {
    Page<Homes> findAll(Pageable pageable);

    Page<Homes> findByUsers_Id(Long userId, Pageable pageable);

    @Query(value = "SELECT DISTINCT h.id,h.rating,h.status, h.address, h.bedroom, h.bathroom, h.price_by_day, b.checkin, b.checkout, MIN(hi.image) AS image, u.name " +
            "FROM homes h " +
            "         JOIN booking b ON h.id = b.home_id " +
            "         JOIN homes_image hi ON h.id = hi.homes_id " +
            "         JOIN users u ON u.id = h.user_id " +
            "WHERE (:bedroom IS NULL OR h.bedroom = :bedroom) " +
            "   OR (:bathroom IS NULL OR h.bathroom = :bathroom) " +
            "   OR (:address IS NULL OR h.address LIKE CONCAT('%', :address, '%')) " +
            "   OR ((:start_date IS NULL OR :end_date IS NULL) OR (b.checkin IS NULL OR b.checkout IS NULL) " +
            "    OR (b.checkin >= :start_date AND b.checkin <= :end_date) " +
            "    OR (b.checkout >= :start_date AND b.checkout <= :end_date)) " +
            "   OR ((:min_price IS NULL OR :max_price IS NULL) " +
            "    OR (h.price_by_day BETWEEN :min_price AND :max_price)) " +
            "GROUP BY h.id,h.rating,h.status, h.address, h.bedroom, h.bathroom, h.price_by_day, b.checkin, b.checkout, u.name;",
            nativeQuery = true)
    List<Object> searchHomes(@Param("bedroom") Integer bedroom,
                                 @Param("bathroom") Integer bathroom,
                                 @Param("address") String address,
                                 @Param("start_date") LocalDate startDate,
                                 @Param("end_date") LocalDate endDate,
                                 @Param("min_price") BigDecimal minPrice,
                                 @Param("max_price") BigDecimal maxPrice);


}
