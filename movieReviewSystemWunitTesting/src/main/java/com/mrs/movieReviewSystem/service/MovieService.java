package com.mrs.movieReviewSystem.service;

import com.mrs.movieReviewSystem.entity.Movie;
import com.mrs.movieReviewSystem.exception.MovieNotFoundException;
import com.mrs.movieReviewSystem.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

   @Autowired
   private MovieRepository movieRepository;

    public void addMovie(Movie movie) {
        try {
            if (movie == null) {
                throw new IllegalArgumentException("Movie object cannot be null");
            }
            movieRepository.save(movie);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid input: " + ex.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException("Failed to add movie: " + ex.getMessage());
        }
    }

    public List<Movie> getAllMovies(String genre) {
        try {
            return genre != null ? movieRepository.findByGenre(genre) : movieRepository.findAll();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to retrieve movies: " + ex.getMessage());
        }
    }

    public Movie getMovieById(Long id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("Movie ID cannot be null");
            }
            return movieRepository.findById(id)
                    .orElseThrow(() -> new MovieNotFoundException("Movie not found with ID: " + id));
        } catch (MovieNotFoundException ex) {
            throw ex; // Re-throw custom exception to be handled globally
        } catch (Exception ex) {
            throw new RuntimeException("Failed to retrieve movie: " + ex.getMessage());
        }
    }

    public void updateMovie(Long id, Movie movie) {
        try {
            if (id == null || movie == null) {
                throw new IllegalArgumentException("Movie ID and details cannot be null");
            }
            Movie existingMovie = getMovieById(id);
            existingMovie.setTitle(movie.getTitle());
            existingMovie.setGenre(movie.getGenre());
            existingMovie.setReleaseDate(movie.getReleaseDate());
            existingMovie.setDirector(movie.getDirector());
            movieRepository.save(existingMovie);
        } catch (MovieNotFoundException ex) {
            throw ex; // Re-throw custom exception
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid input: " + ex.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException("Failed to update movie: " + ex.getMessage());
        }
    }

    public void deleteMovie(Long id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("Movie ID cannot be null");
            }
            if (!movieRepository.existsById(id)) {
                throw new MovieNotFoundException("Movie not found with ID: " + id);
            }
            movieRepository.deleteById(id);
        } catch (MovieNotFoundException ex) {
            throw ex; // Re-throw custom exception
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete movie: " + ex.getMessage());
        }
    }

}
