package com.example.homestay.repository;

import com.example.homestay.model.Homes;
import com.example.homestay.model.Review;
import com.example.homestay.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByHomesAndUsers(Homes homes, Users users);

    @Query(nativeQuery = true, value = "select * from review where homes_id = :id")
    Iterable<Review> getReviewByHomeID(@Param("id") Long id);

    @Query(nativeQuery = true, value = "select ROUND(AVG(rating), 2) as rating from review where homes_id = :id")
    Optional<Float> countRatingAvg(@Param("id") Long id);

    @Query(nativeQuery = true, value = "select * from review where homes_id = :homes_id and users_id =:user_id")
    Optional<Review> findByHomeIdAndUserId(@Param("homes_id") Long homes_id, @Param("user_id") Long user_id);
}
