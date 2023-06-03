package com.example.homestay.repository;

import com.example.homestay.model.Homes;
import com.example.homestay.model.Review;
import com.example.homestay.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.Optional;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByHomesAndUsers(Homes homes, Users users);

    @Query(nativeQuery = true, value = "select * from review where homes_id = :id")
    Iterable<Review> getReviewByHomeID(@Param("id") Long id);
}
