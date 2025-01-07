package com.mrs.movieReviewSystem.service;

import com.mrs.movieReviewSystem.entity.Movie;
import com.mrs.movieReviewSystem.entity.User;
import com.mrs.movieReviewSystem.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    public List<Movie> searchMovies(String title, String genre, String director) {
        return statisticsRepository.searchMovies(title, genre, director);
    }

    public List<Movie> getTopRatedMovies() {
        return statisticsRepository.findTopRatedMovies();
    }

    public List<Map<String, Object>> getAverageRatingOfMovies() {
        List<Object[]> results = statisticsRepository.findAverageRatingForMovies();
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("movieTitle", result[0]);
            map.put("averageRating", result[1]);
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getMostActiveReviewers() {
        List<Object[]> results = statisticsRepository.findMostActiveReviewers();
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            User user = (User) result[0];
            map.put("userId", user.getId());
            map.put("username", user.getUsername());
            map.put("reviewCount", result[1]);
            return map;
        }).collect(Collectors.toList());
    }
}

