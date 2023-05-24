package com.example.homestay.repository;

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

    @Query(nativeQuery = true, value = "SELECT h.id, h.address, h.name, h.price_by_day, ht.name as loai_phong, u.name,  COUNT( DISTINCT b.id) AS booking_count,\n" +
            "       JSON_ARRAY (GROUP_CONCAT(hi.image)) AS images\n" +
            "FROM homes h\n" +
            "         join booking b on h.id = b.home_id\n" +
            "         join home_type ht on h.home_type_id = ht.id\n" +
            "         join users u on h.user_id = u.id\n" +
            "         join homes_image hi on h.id = hi.homes_id\n" +
            "GROUP BY h.id, h.name\n" +
            "ORDER BY booking_count DESC\n" +
            "LIMIT 5;")
    List<Homes> findTop5(int limit);
}
