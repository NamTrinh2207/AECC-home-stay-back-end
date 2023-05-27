package com.example.homestay.repository;

import com.example.homestay.model.DTO.HomeSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.homestay.model.Homes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeRepository extends JpaRepository<Homes, Long> {
    Page<Homes> findAll(Pageable pageable);

    Page<Homes> findByUsers_Id(Long userId, Pageable pageable);

    @Query(value = "SELECT DISTINCT h.id, h.address, h.bathroom, h.bedroom, h.name as nameHome, h.price_by_day, h.status, b.checkin, b.checkout, MAX(hi.image) AS image, u.name as username, ht.name as homeTypeName " +
            "FROM homes h " +
            "LEFT JOIN home_type ht on ht.id = h.home_type_id " +
            "INNER JOIN booking b ON h.id = b.home_id " +
            "INNER JOIN homes_image hi ON h.id = hi.homes_id " +
            "INNER JOIN users u ON u.id = h.user_id " +
            "GROUP BY h.id, h.address, h.bathroom, h.bedroom, h.name, h.price_by_day, h.status, b.checkin, b.checkout, u.name, ht.name",
            nativeQuery = true)
    List<HomeSearch> getAllSearchHomes();

}
