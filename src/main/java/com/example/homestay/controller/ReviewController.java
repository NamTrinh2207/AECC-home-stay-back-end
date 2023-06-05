package com.example.homestay.controller;

import com.example.homestay.model.DTO.ReviewDto;
import com.example.homestay.model.Homes;
import com.example.homestay.model.Review;
import com.example.homestay.model.Users;
import com.example.homestay.service.home.IHomeService;
import com.example.homestay.service.review.IReviewService;
import com.example.homestay.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("api/review")
public class ReviewController {
    @Autowired
    private IReviewService iReviewService;
    @Autowired
    private IHomeService homeService;
    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<Review>> findAll() {
        Iterable<Review> reviews = iReviewService.findAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> addReview(@RequestBody ReviewDto reviewDto) {
        Homes homes = homeService.findById(reviewDto.getHomeId())
                .orElseThrow(() -> new RuntimeException("Không tim thấy nhà có ID: " + reviewDto.getHomeId()));

        Users users = userService.findById(reviewDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng có ID : " + reviewDto.getUserId()));

        Optional<Review> existingReview = iReviewService.findByHomesAndUsers(homes, users);
        if (existingReview.isPresent()) {
            return ResponseEntity.badRequest().body("Người dùng này đã đánh giá ngôi nhà này.");
        }

        Review review = new Review();
        review.setHomes(homes);
        review.setUsers(users);
        review.setRating(reviewDto.getRating());
        String defaultComment = "Người dùng này không có bình luận nào!";
        if (reviewDto.getComment() == null) {
            reviewDto.setComment(defaultComment);
        }
        review.setComment(reviewDto.getComment());

        iReviewService.save(review);

        return ResponseEntity.ok("Thêm đánh giá thành công.");
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Optional<Review>> viewDetail(@PathVariable Long id) {
        Optional<Review> reviewOptional = iReviewService.findById(id);
        if (reviewOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(reviewOptional, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable("id") Long id, @RequestBody ReviewDto reviewDto) {
        Review review = iReviewService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy review có ID: " + id));

        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());

        iReviewService.save(review);

        return ResponseEntity.ok("Đã sửa review thành công.");
    }

    @GetMapping("/get-review/{id}")
    public ResponseEntity<Iterable<Review>> getReviewByHomeId(@PathVariable("id") Long id) {
        Iterable<Review> reviews = iReviewService.getReviewByHomeID(id);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/avg/{id}")
    public ResponseEntity<Optional<Float>> countRatingAvg(@PathVariable("id") Long id) {
        Optional<Float> reviewOptional = iReviewService.countRatingAvg(id);
        if (reviewOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(reviewOptional, HttpStatus.OK);
        }
    }

    @GetMapping("/get-first/home-id={homes_id}/user-id={user_id}")
    public ResponseEntity<Optional<Review>> getReviewByHomeIdAndUserId (@PathVariable Long homes_id, @PathVariable Long user_id) {
        Optional<Review> review = iReviewService.findByHomeIdAndUserId(homes_id, user_id);
        if (review.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(review, HttpStatus.OK);
        }
    }
}
