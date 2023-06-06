package com.example.homestay.service.review;

import com.example.homestay.model.Homes;
import com.example.homestay.model.Review;
import com.example.homestay.model.Users;
import com.example.homestay.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService implements IReviewService {
    @Autowired
    private IReviewRepository iReviewRepository;

    @Override
    public Iterable<Review> findAll() {
        return iReviewRepository.findAll();
    }

    @Override
    public Optional<Review> findById(Long id) {
        return iReviewRepository.findById(id);
    }

    @Override
    public Review save(Review review) {
        return iReviewRepository.save(review);
    }

    @Override
    public void remove(Long id) {
        iReviewRepository.deleteById(id);
    }

    @Override
    public Optional<Review> findByHomesAndUsers(Homes homes, Users users) {
        return iReviewRepository.findByHomesAndUsers(homes, users);
    }

    @Override
    public Iterable<Review> getReviewByHomeID(Long id) {
        return iReviewRepository.getReviewByHomeID(id);
    }

    @Override
    public Optional<Float> countRatingAvg(Long id) {
        return iReviewRepository.countRatingAvg(id);
    }

    @Override
    public Optional<Review> findByHomeIdAndUserId(Long homes_id, Long user_id) {
        return iReviewRepository.findByHomeIdAndUserId(homes_id,user_id);
    }
}
