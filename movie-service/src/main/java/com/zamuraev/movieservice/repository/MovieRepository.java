package com.zamuraev.movieservice.repository;

import com.zamuraev.movieservice.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> getMovieByGenreOrderByYearDesc(String genre);
}
