package com.example.homestay.service.review;

import com.example.homestay.model.Homes;
import com.example.homestay.model.Review;
import com.example.homestay.model.Users;
import com.example.homestay.service.IGeneralService;

import java.util.Optional;

public interface IReviewService extends IGeneralService<Review> {
    Optional<Review> findByHomesAndUsers(Homes homes, Users users);
}
