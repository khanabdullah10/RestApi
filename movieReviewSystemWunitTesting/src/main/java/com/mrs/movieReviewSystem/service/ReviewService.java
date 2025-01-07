package com.mrs.movieReviewSystem.service;


import com.mrs.movieReviewSystem.entity.Movie;
import com.mrs.movieReviewSystem.entity.Review;
import com.mrs.movieReviewSystem.entity.User;
import com.mrs.movieReviewSystem.exception.MovieNotFoundException;
import com.mrs.movieReviewSystem.exception.ReviewNotFoundException;
import com.mrs.movieReviewSystem.exception.UserNotFoundException;
import com.mrs.movieReviewSystem.repository.MovieRepository;
import com.mrs.movieReviewSystem.repository.ReviewRepository;
import com.mrs.movieReviewSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  ReviewService {

    @Autowired
    private  ReviewRepository reviewRepository;
    @Autowired
    private  MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;




    public Review addReview(Long movieId, Review review) {
        // Validate the existence of the movie
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with ID: " + movieId));

        // Validate the existence of the user
        User user = userRepository.findById(review.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + review.getUser().getId()));

        // Set the movie and user in the review entity
        review.setMovie(movie);
        review.setUser(user);

        // Save the review
        return reviewRepository.save(review);
    }




    public List<Review> getReviewsByMovie(Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }


    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + id));
    }


    public Review updateReview(Long id, Review review) {
        Review existingReview = getReviewById(id);
        existingReview.setRating(review.getRating());
        existingReview.setComment(review.getComment());
        return reviewRepository.save(existingReview);
    }


    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException("Review not found with ID: " + id);
        }
        reviewRepository.deleteById(id);
    }
}

