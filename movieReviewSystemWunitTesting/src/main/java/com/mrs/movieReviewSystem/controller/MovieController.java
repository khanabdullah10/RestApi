package com.mrs.movieReviewSystem.controller;

import com.mrs.movieReviewSystem.entity.Movie;
import com.mrs.movieReviewSystem.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @PostMapping
    public ResponseEntity<String> addMovie(@Valid @RequestBody Movie movie) {
        movieService.addMovie(movie);
        return ResponseEntity.ok().body("Added Successfully !");
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(required = false) String genre) {
        return ResponseEntity.ok(movieService.getAllMovies(genre));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMovie(@PathVariable Long id, @Valid @RequestBody Movie movie) {
        movieService.updateMovie(id, movie);
        return ResponseEntity.ok().body("Updated Successfully !");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie deleted successfully.");
    }
}

