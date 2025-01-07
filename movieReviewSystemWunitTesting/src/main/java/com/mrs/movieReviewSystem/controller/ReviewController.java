package com.mrs.movieReviewSystem.controller;
import com.mrs.movieReviewSystem.entity.Review;
import com.mrs.movieReviewSystem.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies/{movieId}/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @PostMapping
    public ResponseEntity<Review> addReview(@PathVariable Long movieId, @Valid @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.addReview(movieId, review));
    }

    @GetMapping
    public ResponseEntity<List<Review>> getReviewsByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(reviewService.getReviewsByMovie(movieId));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId, @Valid @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.updateReview(reviewId, review));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted successfully.");
    }
}

