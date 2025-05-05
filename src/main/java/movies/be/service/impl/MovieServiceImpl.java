/*
 * @ (#) MovieServiceImpl.java 1.0 2025-05-05
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */

package movies.be.service.impl;

import movies.be.dto.EpisodeDto;
import movies.be.dto.MovieDto;
import movies.be.exception.ErrorMessages;
import movies.be.exception.MovieException;
import movies.be.model.*;
import movies.be.repository.MovieRepository;
import movies.be.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Converts a Movie entity to a MovieDto.
     *
     * @param movie the Movie entity to convert
     * @return the converted MovieDto
     */
    private MovieDto convertToDto(Movie movie) {
        MovieDto dto = new MovieDto();
        dto.setMovieId(movie.getMovieId());
        dto.setTitle(movie.getTitle());
        dto.setType(movie.getType().toString());
        dto.setDescription(movie.getDescription());
        dto.setThumbnail(movie.getThumbnail());
        dto.setVideoUrl(movie.getVideoUrl());
        dto.setDuration(movie.getDuration()); // Ánh xạ duration
        dto.setCountryId(movie.getCountry() != null ? movie.getCountry().getId() : null);
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setHot(movie.isHot());
        dto.setNew(movie.isNew());
        dto.setCreatedAt(movie.getCreatedAt());

        // Convert episodes for PHIM_BO
        if (movie.getEpisodes() != null && !movie.getEpisodes().isEmpty()) {
            List<EpisodeDto> episodeDtos = movie.getEpisodes().stream()
                    .map(this::convertEpisodeToDto)
                    .collect(Collectors.toList());
            dto.setEpisodes(episodeDtos);
        }

        // Calculate average rating
        if (movie.getRatings() != null && !movie.getRatings().isEmpty()) {
            double avgRating = movie.getRatings().stream()
                    .mapToDouble(Rating::getRatingValue)
                    .average()
                    .orElse(0.0);
            dto.setAverageRating(avgRating);
        } else {
            dto.setAverageRating(0.0);
        }

        // Convert category IDs
        if (movie.getCategories() != null) {
            List<Long> categoryIds = movie.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toList());
            dto.setCategoryIds(categoryIds);
        }

        return dto;
    }

    /**
     * Converts an Episode entity to an EpisodeDto.
     *
     * @param episode the Episode entity to convert
     * @return the converted EpisodeDto
     */
    private EpisodeDto convertEpisodeToDto(Episode episode) {
        EpisodeDto dto = new EpisodeDto();
        dto.setEpisodeId(episode.getEpisodeId());
        dto.setEpisodeNumber(episode.getEpisodeNumber());
        dto.setTitle(episode.getTitle());
        dto.setThumbnail(episode.getThumbnail());
        dto.setVideoUrl(episode.getVideoUrl());
        dto.setDuration(episode.getDuration());
        dto.setCreatedAt(episode.getCreatedAt());
        return dto;
    }

    /**
     * Converts a MovieDto to a Movie entity.
     *
     * @param dto the MovieDto to convert
     * @return the converted Movie entity
     */
    private Movie convertToEntity(MovieDto dto) {
        Movie movie = new Movie();
        movie.setMovieId(dto.getMovieId());
        movie.setTitle(dto.getTitle());
        movie.setType(Movie.MovieType.valueOf(dto.getType()));
        movie.setDescription(dto.getDescription());
        movie.setThumbnail(dto.getThumbnail());
        movie.setVideoUrl(dto.getVideoUrl());
        movie.setDuration(dto.getDuration()); // Ánh xạ duration
        if (dto.getCountryId() != null) {
            Country country = new Country();
            country.setId(dto.getCountryId());
            movie.setCountry(country);
        }
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setHot(dto.isHot());
        movie.setNew(dto.isNew());
        movie.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());

        // Convert episodes for PHIM_BO
        if (dto.getEpisodes() != null && !dto.getEpisodes().isEmpty()) {
            List<Episode> episodes = dto.getEpisodes().stream()
                    .map(episodeDto -> {
                        Episode episode = new Episode();
                        episode.setEpisodeId(episodeDto.getEpisodeId());
                        episode.setEpisodeNumber(episodeDto.getEpisodeNumber());
                        episode.setTitle(episodeDto.getTitle());
                        episode.setThumbnail(episodeDto.getThumbnail());
                        episode.setVideoUrl(episodeDto.getVideoUrl());
                        episode.setDuration(episodeDto.getDuration());
                        episode.setCreatedAt(episodeDto.getCreatedAt() != null ? episodeDto.getCreatedAt() : LocalDateTime.now());
                        episode.setMovie(movie);
                        return episode;
                    })
                    .collect(Collectors.toList());
            movie.setEpisodes(episodes);
        }

        return movie;
    }

    /**
     * Validates the movie data based on its type (PHIM_LE or PHIM_BO).
     *
     * @param movieDto the MovieDto to validate
     * @throws MovieException if validation fails
     */
    private void validateMovieData(MovieDto movieDto) {
        if (movieDto == null || movieDto.getTitle() == null || movieDto.getTitle().trim().isEmpty()) {
            logger.error("Invalid movie data: title cannot be null or empty");
            throw new MovieException(ErrorMessages.INVALID_MOVIE_DATA_MESSAGE);
        }

        if (movieDto.getType() == null) {
            logger.error("Invalid movie data: type cannot be null");
            throw new MovieException("Movie type cannot be null");
        }

        if (movieDto.getType().equals(Movie.MovieType.PHIM_LE.toString())) {
            if (movieDto.getVideoUrl() == null || movieDto.getVideoUrl().trim().isEmpty()) {
                logger.error("PHIM_LE validation failed: video URL is required");
                throw new MovieException(ErrorMessages.PHIM_LE_VIDEO_URL_REQUIRED_MESSAGE);
            }
            if (movieDto.getEpisodes() != null && !movieDto.getEpisodes().isEmpty()) {
                logger.error("PHIM_LE validation failed: should not have episodes");
                throw new MovieException(ErrorMessages.PHIM_LE_NO_EPISODES_MESSAGE);
            }
            // Thêm validation cho duration của PHIM_LE
            if (movieDto.getDuration() == null || movieDto.getDuration() <= 0) {
                logger.error("PHIM_LE validation failed: duration must be a positive integer");
                throw new MovieException("PHIM_LE must have a valid duration (positive integer)");
            }
        } else if (movieDto.getType().equals(Movie.MovieType.PHIM_BO.toString())) {
            if (movieDto.getVideoUrl() != null && !movieDto.getVideoUrl().trim().isEmpty()) {
                logger.error("PHIM_BO validation failed: should not have a direct video URL");
                throw new MovieException(ErrorMessages.PHIM_BO_NO_VIDEO_URL_MESSAGE);
            }
            if (movieDto.getEpisodes() == null || movieDto.getEpisodes().isEmpty()) {
                logger.error("PHIM_BO validation failed: must have at least one episode");
                throw new MovieException(ErrorMessages.PHIM_BO_EPISODES_REQUIRED_MESSAGE);
            }
            if (movieDto.getDuration() != null) {
                logger.error("PHIM_BO validation failed: should not have a duration");
                throw new MovieException("PHIM_BO should not have a duration; duration is for episodes only");
            }
        } else {
            logger.error("Invalid movie type: {}", movieDto.getType());
            throw new MovieException(String.format(ErrorMessages.INVALID_MOVIE_TYPE_MESSAGE, movieDto.getType()));
        }
    }

    /**
     * Retrieves all movies from the repository.
     *
     * @return List of all movies as DTOs
     * @throws MovieException if an error occurs while fetching movies
     */
    @Override
    public List<MovieDto> getAllMovies() {
        logger.info("Fetching all movies");
        try {
            List<Movie> movies = movieRepository.findAll();
            List<MovieDto> movieDtos = movies.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            logger.debug("Found {} movies", movieDtos.size());
            return movieDtos;
        } catch (Exception e) {
            logger.error("Failed to fetch all movies: {}", e.getMessage(), e);
            throw new MovieException("Unable to retrieve movies due to server error", e);
        }
    }

    /**
     * Retrieves a movie by its ID.
     *
     * @param id The ID of the movie to retrieve
     * @return The movie DTO
     * @throws MovieException if the movie with the given ID does not exist
     */
    @Override
    public MovieDto getMovieById(int id) {
        logger.info("Fetching movie with ID: {}", id);
        try {
            Optional<Movie> movie = movieRepository.findById(  id);
            if (movie.isEmpty()) {
                logger.warn("Movie with ID {} not found", id);
                throw new MovieException(String.format(ErrorMessages.MOVIE_NOT_FOUND_MESSAGE, id));
            }
            logger.debug("Successfully retrieved movie with ID: {}", id);
            return convertToDto(movie.get());
        } catch (MovieException e) {
            throw e; // Re-throw MovieException for controller to handle
        } catch (Exception e) {
            logger.error("Failed to fetch movie with ID {}: {}", id, e.getMessage(), e);
            throw new MovieException("Failed to fetch movie with ID " + id + " due to server error", e);
        }
    }

    /**
     * Creates a new movie in the repository.
     *
     * @param movieDto The movie DTO to create
     * @return The created movie DTO
     * @throws MovieException if the movie data is null or invalid
     */
    @Override
    public MovieDto createMovie(MovieDto movieDto) {
        logger.info("Creating new movie: {}", movieDto != null ? movieDto.getTitle() : "null");
        validateMovieData(movieDto);

        try {
            Movie movie = convertToEntity(movieDto);
            Movie savedMovie = movieRepository.save(movie);
            logger.debug("Successfully created movie with ID: {}", savedMovie.getMovieId());
            return convertToDto(savedMovie);
        } catch (Exception e) {
            logger.error("Failed to create movie: {}", e.getMessage(), e);
            throw new MovieException("Failed to create movie due to server error", e);
        }
    }

    /**
     * Updates an existing movie in the repository.
     *
     * @param id The ID of the movie to update
     * @param movieDto The updated movie DTO
     * @return The updated movie DTO
     * @throws MovieException if the movie with the given ID does not exist or data is invalid
     */
    @Override
    public MovieDto updateMovie(int id, MovieDto movieDto) {
        logger.info("Updating movie with ID: {}", id);
        validateMovieData(movieDto);

        try {
            Optional<Movie> existingMovie = movieRepository.findById(  id);
            if (existingMovie.isEmpty()) {
                logger.warn("Movie with ID {} not found for update", id);
                throw new MovieException(String.format(ErrorMessages.MOVIE_NOT_FOUND_MESSAGE, id));
            }

            Movie movieToUpdate = existingMovie.get();
            movieToUpdate.setTitle(movieDto.getTitle());
            movieToUpdate.setType(Movie.MovieType.valueOf(movieDto.getType()));
            movieToUpdate.setDescription(movieDto.getDescription());
            movieToUpdate.setThumbnail(movieDto.getThumbnail());
            movieToUpdate.setVideoUrl(movieDto.getVideoUrl());
            movieToUpdate.setDuration(movieDto.getDuration()); // Cập nhật duration
            if (movieDto.getCountryId() != null) {
                Country country = new Country();
                country.setId(movieDto.getCountryId());
                movieToUpdate.setCountry(country);
            }
            movieToUpdate.setReleaseYear(movieDto.getReleaseYear());
            movieToUpdate.setHot(movieDto.isHot());
            movieToUpdate.setNew(movieDto.isNew());

            // Update episodes for PHIM_BO
            if (movieDto.getType().equals(Movie.MovieType.PHIM_BO.toString())) {
                movieToUpdate.getEpisodes().clear();
                List<Episode> updatedEpisodes = movieDto.getEpisodes().stream()
                        .map(episodeDto -> {
                            Episode episode = new Episode();
                            episode.setEpisodeId(episodeDto.getEpisodeId());
                            episode.setEpisodeNumber(episodeDto.getEpisodeNumber());
                            episode.setTitle(episodeDto.getTitle());
                            episode.setThumbnail(episodeDto.getThumbnail());
                            episode.setVideoUrl(episodeDto.getVideoUrl());
                            episode.setDuration(episodeDto.getDuration());
                            episode.setCreatedAt(episodeDto.getCreatedAt() != null ? episodeDto.getCreatedAt() : LocalDateTime.now());
                            episode.setMovie(movieToUpdate);
                            return episode;
                        })
                        .collect(Collectors.toList());
                movieToUpdate.getEpisodes().addAll(updatedEpisodes);
            } else {
                movieToUpdate.getEpisodes().clear();
            }

            Movie updatedMovie = movieRepository.save(movieToUpdate);
            logger.debug("Successfully updated movie with ID: {}", id);
            return convertToDto(updatedMovie);
        } catch (MovieException e) {
            throw e; // Re-throw MovieException for controller to handle
        } catch (Exception e) {
            logger.error("Failed to update movie with ID {}: {}", id, e.getMessage(), e);
            throw new MovieException("Failed to update movie with ID " + id + " due to server error", e);
        }
    }

    /**
     * Deletes a movie from the repository.
     *
     * @param id The ID of the movie to delete
     * @throws MovieException if the movie with the given ID does not exist
     */
    @Override
    public void deleteMovie(int id) {
        logger.info("Deleting movie with ID: {}", id);
        try {
            Optional<Movie> movie = movieRepository.findById(  id);
            if (movie.isEmpty()) {
                logger.warn("Movie with ID {} not found for deletion", id);
                throw new MovieException(String.format(ErrorMessages.MOVIE_NOT_FOUND_MESSAGE, id));
            }

            movieRepository.deleteById(  id);
            logger.debug("Successfully deleted movie with ID: {}", id);
        } catch (MovieException e) {
            throw e; // Re-throw MovieException for controller to handle
        } catch (Exception e) {
            logger.error("Failed to delete movie with ID {}: {}", id, e.getMessage(), e);
            throw new MovieException("Failed to delete movie with ID " + id + " due to server error", e);
        }
    }
}