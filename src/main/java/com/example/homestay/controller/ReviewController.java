package com.example.homestay.controller;

import com.example.homestay.model.Review;
import com.example.homestay.service.review.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("homes/review")
public class ReviewController {
    @Autowired
    private IReviewService iReviewService;
    @GetMapping("/list")
    public ResponseEntity<Iterable<Review>> findAll(){
        Iterable<Review> reviews = iReviewService.findAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Review> create(@RequestBody Review review){
        return new ResponseEntity<>(iReviewService.save(review), HttpStatus.CREATED);
    }
    @GetMapping("view/{id}")
    public ResponseEntity<Optional<Review>> viewDetail(@PathVariable Long id){
        Optional<Review> reviewOptional = iReviewService.findById(id);
        if (reviewOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(reviewOptional, HttpStatus.OK);
        }
    }
}
