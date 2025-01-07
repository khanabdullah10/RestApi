package com.mrs.movieReviewSystem.repository;

import com.mrs.movieReviewSystem.entity.Movie;
import com.mrs.movieReviewSystem.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {


    @Query("SELECT m FROM Movie m WHERE " +
            "(:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:genre IS NULL OR LOWER(m.genre) LIKE LOWER(CONCAT('%', :genre, '%'))) AND " +
            "(:director IS NULL OR LOWER(m.director) LIKE LOWER(CONCAT('%', :director, '%')))")
    List<Movie> searchMovies(@Param("title") String title,
                             @Param("genre") String genre,
                             @Param("director") String director);

    @Query("SELECT m FROM Movie m JOIN Review r ON m.id = r.movie.id " +
            "GROUP BY m.id ORDER BY AVG(r.rating) DESC")
    List<Movie> findTopRatedMovies();

    @Query("SELECT m.title AS movieTitle, AVG(r.rating) AS averageRating " +
            "FROM Review r JOIN r.movie m GROUP BY m.id")
    List<Object[]> findAverageRatingForMovies();

    @Query("SELECT u, COUNT(r) AS reviewCount " +
            "FROM Review r JOIN r.user u GROUP BY u.id ORDER BY reviewCount DESC")
    List<Object[]> findMostActiveReviewers();
}

