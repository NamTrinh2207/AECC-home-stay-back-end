package com.example.homestay.service.review;

import com.example.homestay.model.Homes;
import com.example.homestay.model.Review;
import com.example.homestay.model.Users;
import com.example.homestay.service.IGeneralService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IReviewService extends IGeneralService<Review> {
    Optional<Review> findByHomesAndUsers(Homes homes, Users users);

    Iterable<Review> getReviewByHomeID(@Param("id") Long id);

    Optional<Float> countRatingAvg(Long id);

    Optional<Review> findByHomeIdAndUserId(Long homes_id, Long user_id);
}
