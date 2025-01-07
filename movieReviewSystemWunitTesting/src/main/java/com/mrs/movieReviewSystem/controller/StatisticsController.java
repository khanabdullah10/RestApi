package com.mrs.movieReviewSystem.controller;

import com.mrs.movieReviewSystem.entity.Movie;
import com.mrs.movieReviewSystem.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String director) {
        List<Movie> movies = statisticsService.searchMovies(title, genre, director);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<Movie>> getTopRatedMovies() {
        List<Movie> movies = statisticsService.getTopRatedMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/average-rating")
    public ResponseEntity<List<Map<String, Object>>> getAverageRatingOfMovies() {
        List<Map<String, Object>> ratings = statisticsService.getAverageRatingOfMovies();
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/most-active-reviewers")
    public ResponseEntity<List<Map<String, Object>>> getMostActiveReviewers() {
        List<Map<String, Object>> reviewers = statisticsService.getMostActiveReviewers();
        return new ResponseEntity<>(reviewers, HttpStatus.OK);
    }
}

