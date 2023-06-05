package com.example.homestay.repository;

import com.example.homestay.model.DTO.HomeSearch;
import com.example.homestay.model.DTO.IncomeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.homestay.model.Homes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomeRepository extends JpaRepository<Homes, Long> {
    
    List<Homes> findByUsers_Id(Long userId);

    List<Homes> findHomesByHomeTypeId(Long id);

    @Query(value = "SELECT DISTINCT h.id, h.address, h.bathroom, h.bedroom, h.name as homename, h.price_by_day as pricebyday, h.status, b.checkin, b.checkout, MAX(hi.image) AS image, u.name as username, ht.name as hometype " +
            "FROM homes h " +
            "INNER JOIN home_type ht on ht.id = h.home_type_id " +
            "INNER JOIN booking b ON h.id = b.home_id " +
            "INNER JOIN homes_image hi ON h.id = hi.homes_id " +
            "INNER JOIN users u ON u.id = h.user_id " +
            "GROUP BY h.id, h.address, h.bathroom, h.bedroom, h.name, h.price_by_day, h.status, b.checkin, b.checkout, u.name, ht.name " +
            "ORDER BY h.id ASC",
            nativeQuery = true)
    List<HomeSearch> getAllSearchHomes();

    @Query(value = "SELECT h.id AS id, h.name AS name, DATE_FORMAT(b.checkin, '%Y-%m') AS month, SUM(b.total_price) AS income " +
            "FROM booking b " +
            "INNER JOIN homes h ON b.home_id = h.id " +
            "INNER JOIN rental_history rh ON b.id = rh.booking_id " +
            "INNER JOIN users u ON h.user_id = u.id " +
            "WHERE b.is_paid = 1 AND u.id = :userId " +
            "GROUP BY h.id, h.name, DATE_FORMAT(b.checkin, '%Y-%m') " +
            "ORDER BY h.id, DATE_FORMAT(b.checkin, '%Y-%m')", nativeQuery = true)
    List<IncomeDTO> getUserIncome(@Param("userId") Long userId);

    @Query(
            nativeQuery = true,
            value = "UPDATE homes\n" +
                    "SET status = 3\n" +
                    "WHERE id = :id;")
    Optional<Homes> updateStatusAfterBooking(@Param("id") Long id);
}
