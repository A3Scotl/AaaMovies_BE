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
import movies.be.exception.MovieException;
import movies.be.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        try {
            List<MovieDto> movies = movieService.getAllMovies();
            logger.debug("Successfully retrieved {} movies", movies.size());
            return ResponseEntity.ok(movies);
        } catch (MovieException e) {
            logger.error("Failed to fetch all movies: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long id) {
        logger.info("Received request to fetch movie with ID: {}", id);
        try {
            MovieDto movie = movieService.getMovieById(id);
            logger.debug("Successfully retrieved movie with ID: {}", id);
            return ResponseEntity.ok(movie);
        } catch (MovieException e) {
            logger.error("Failed to fetch movie with ID {}: {}", id, e.getMessage());
            if (e.getMessage().contains("does not exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiError(HttpStatus.NOT_FOUND, "Movie with ID " + id + " does not exist"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch movie: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody MovieDto movieDto) {
        logger.info("Received request to create movie: {}", movieDto != null ? movieDto.getTitle() : "null");
        try {
            MovieDto createdMovie = movieService.createMovie(movieDto);
            logger.debug("Successfully created movie with ID: {}", createdMovie.getMovieId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
        } catch (MovieException e) {
            logger.error("Failed to create movie: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError(HttpStatus.BAD_REQUEST, "Failed to create movie: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @RequestBody MovieDto movieDto) {
        logger.info("Received request to update movie with ID: {}", id);
        try {
            MovieDto updatedMovie = movieService.updateMovie(id, movieDto);
            logger.debug("Successfully updated movie with ID: {}", id);
            return ResponseEntity.ok(updatedMovie);
        } catch (MovieException e) {
            logger.error("Failed to update movie with ID {}: {}", id, e.getMessage());
            if (e.getMessage().contains("does not exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiError(HttpStatus.NOT_FOUND, "Movie with ID " + id + " does not exist"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update movie: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        logger.info("Received request to delete movie with ID: {}", id);
        try {
            movieService.deleteMovie(id);
            logger.debug("Successfully deleted movie with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (MovieException e) {
            logger.error("Failed to delete movie with ID {}: {}", id, e.getMessage());
            if (e.getMessage().contains("does not exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiError(HttpStatus.NOT_FOUND, "Movie with ID " + id + " does not exist"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete movie: " + e.getMessage()));
        }
    }
    @PostMapping("/{movieId}/episodes")
    public ResponseEntity<MovieDto> addEpisode(@PathVariable Long movieId, @RequestBody EpisodeDto episodeDto) {
        MovieDto updatedMovie = movieService.addEpisodeToMovie(movieId, episodeDto);
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }
    @PutMapping("/{id}/toggle-active")
    public ResponseEntity<MovieDto> toggleActive(@PathVariable Long id, @RequestBody ToggleActiveRequest request) {
        MovieDto updatedMovie = movieService.changeActive(id, request.getIsActive());
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }
}