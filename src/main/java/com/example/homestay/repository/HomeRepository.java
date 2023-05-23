package com.example.homestay.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.homestay.model.Homes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface HomeRepository extends JpaRepository<Homes, Long> {
    Page<Homes> findAll(Pageable pageable);

    Page<Homes> findByUsers_Id(Long userId, Pageable pageable);

    @Query(value = "select distinct h.id, h.address, h.bedroom, h.bathroom, h.price_by_day, b.checkin, b.checkout " +
            "from homes h " +
            "         join booking b on h.id = b.home_id " +
            "where (:address is null or h.address like concat('%', :address, '%')) " +
            "    and (:bedroom is null or h.bedroom = :bedroom) " +
            "    and (:bathroom is null or h.bathroom = :bathroom) " +
            "    and (:min_price is null or :max_price is null) or (h.price_by_day between :min_price and :max_price) " +
            "    and ( " +
            "              (b.checkin is null or b.checkout is null) " +
            "              or (b.checkin >= :start_date and b.checkin <= :end_date) " +
            "              or (b.checkout >= :start_date and b.checkout <= :end_date) " +
            "          ) " +
            "group by h.id, b.id;", nativeQuery = true)
    List<Homes> findByCriteria(@Param("bedroom") Integer bedroom,
                               @Param("bathroom") Integer bathroom,
                               @Param("address") String address,
                               @Param("start_date") Date startDate,
                               @Param("end_date") Date endDate,
                               @Param("min_price") Long minPrice,
                               @Param("max_price") Long maxPrice);

}
