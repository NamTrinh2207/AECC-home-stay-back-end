package com.example.homestay.service.review;

import com.example.homestay.model.Review;
import com.example.homestay.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ReviewService implements IReviewService{
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
}