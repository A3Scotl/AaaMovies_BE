/*
 * @ (#) MovieController.java 1.0 2025-05-05
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */
package movies.be.controller;

import lombok.RequiredArgsConstructor;
import movies.be.dto.EpisodeDto;
import movies.be.dto.MovieDto;
import movies.be.dto.ToggleActiveRequest;
import movies.be.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        logger.info("Received request to fetch all movies");
        List<MovieDto> movies = movieService.getAllMovies();
        logger.debug("Successfully retrieved {} movies", movies.size());
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        logger.info("Received request to fetch movie with ID: {}", id);
        MovieDto movie = movieService.getMovieById(id);
        logger.debug("Successfully retrieved movie with ID: {}", id);
        return ResponseEntity.ok(movie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto) {
        logger.info("Received request to create movie: {}", movieDto != null ? movieDto.getTitle() : "null");
        MovieDto createdMovie = movieService.createMovie(movieDto);
        logger.debug("Successfully created movie with ID: {}", createdMovie.getMovieId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id, @RequestBody MovieDto movieDto) {
        logger.info("Received request to update movie with ID: {}", id);
        MovieDto updatedMovie = movieService.updateMovie(id, movieDto);
        logger.debug("Successfully updated movie with ID: {}", id);
        return ResponseEntity.ok(updatedMovie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        logger.info("Received request to delete movie with ID: {}", id);
        movieService.deleteMovie(id);
        logger.debug("Successfully deleted movie with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{movieId}/episodes")
    public ResponseEntity<MovieDto> addEpisode(@PathVariable Long movieId, @RequestBody EpisodeDto episodeDto) {
        logger.info("Received request to add episode to movie with ID: {}", movieId);
        MovieDto updatedMovie = movieService.addEpisodeToMovie(movieId, episodeDto);
        logger.debug("Successfully added episode to movie with ID: {}", movieId);
        return ResponseEntity.ok(updatedMovie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/toggle-active")
    public ResponseEntity<MovieDto> toggleActive(@PathVariable Long id, @RequestBody ToggleActiveRequest request) {
        logger.info("Received request to toggle active status for movie with ID: {}", id);
        MovieDto updatedMovie = movieService.changeActive(id, request.getIsActive());
        logger.debug("Successfully toggled active status for movie with ID: {}", id);
        return ResponseEntity.ok(updatedMovie);
    }
}