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
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/hot")
    public ResponseEntity<List<MovieDto>> getHotMovies() {
        logger.info("Received request to fetch hot movies");
        List<MovieDto> movies = movieService.getHotMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/new")
    public ResponseEntity<List<MovieDto>> getNewMovies() {
        logger.info("Received request to fetch new movies");
        List<MovieDto> movies = movieService.getNewMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/series")
    public ResponseEntity<List<MovieDto>> getSeriesMovies() {
        logger.info("Received request to fetch series movies");
        List<MovieDto> movies = movieService.getSeriesMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/singles")
    public ResponseEntity<List<MovieDto>> getSingleMovies() {
        logger.info("Received request to fetch single movies");
        List<MovieDto> movies = movieService.getSingleMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{movieId}/episodes")
    public ResponseEntity<List<EpisodeDto>> getEpisodesByMovieId(@PathVariable Long movieId) {
        logger.info("Received request to fetch episodes for movie ID: {}", movieId);
        List<EpisodeDto> episodes = movieService.getEpisodesByMovieId(movieId);
        return ResponseEntity.ok(episodes);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<MovieDto>> getTopRatedMovies() {
        logger.info("Received request to fetch top-rated movies");
        List<MovieDto> movies = movieService.getTopRatedMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<MovieDto>> getPopularMovies() {
        logger.info("Received request to fetch popular movies");
        List<MovieDto> movies = movieService.getPopularMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/by-country/{countryId}")
    public ResponseEntity<List<MovieDto>> getMoviesByCountry(@PathVariable Long countryId) {
        logger.info("Received request to fetch movies by country ID: {}", countryId);
        List<MovieDto> movies = movieService.getMoviesByCountry(countryId);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<MovieDto>> getMoviesByCategory(@PathVariable Long categoryId) {
        logger.info("Received request to fetch movies by category ID: {}", categoryId);
        List<MovieDto> movies = movieService.getMoviesByCategory(categoryId);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieDto>> searchMovies(@RequestParam String value) {
        logger.info("Received request to search movies with query: {}", value);
        List<MovieDto> movies = movieService.searchMovies(value);
        return ResponseEntity.ok(movies);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto) {
        logger.info("Received request to create movie: {}", movieDto != null ? movieDto.getTitle() : "null");
        MovieDto createdMovie = movieService.createMovie(movieDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id, @RequestBody MovieDto movieDto) {
        logger.info("Received request to update movie with ID: {}", id);
        MovieDto updatedMovie = movieService.updateMovie(id, movieDto);
        return ResponseEntity.ok(updatedMovie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        logger.info("Received request to delete movie with ID: {}", id);
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{movieId}/episodes")
    public ResponseEntity<MovieDto> addEpisode(@PathVariable Long movieId, @RequestBody EpisodeDto episodeDto) {
        logger.info("Received request to add episode to movie with ID: {}", movieId);
        MovieDto updatedMovie = movieService.addEpisodeToMovie(movieId, episodeDto);
        return ResponseEntity.ok(updatedMovie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/toggle-active")
    public ResponseEntity<MovieDto> toggleActive(@PathVariable Long id, @RequestBody ToggleActiveRequest request) {
        logger.info("Received request to toggle active status for movie with ID: {}", id);
        MovieDto updatedMovie = movieService.changeActive(id, request.getIsActive());
        return ResponseEntity.ok(updatedMovie);
    }
}