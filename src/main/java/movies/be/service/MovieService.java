/*
 * @ (#) MovieService.java 1.0 2025-05-05
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */

package movies.be.service;

import movies.be.dto.MovieDto;

import java.util.List;

public interface MovieService {

    /**
     * Retrieves all movies.
     *
     * @return List of all movies as DTOs
     */
    List<MovieDto> getAllMovies();

    /**
     * Retrieves a movie by its ID.
     *
     * @param id The ID of the movie to retrieve
     * @return The movie DTO
     */
    MovieDto getMovieById(int id);

    /**
     * Creates a new movie.
     *
     * @param movieDto The movie DTO to create
     * @return The created movie DTO
     */
    MovieDto createMovie(MovieDto movieDto);

    /**
     * Updates an existing movie.
     *
     * @param id The ID of the movie to update
     * @param movieDto The updated movie DTO
     * @return The updated movie DTO
     */
    MovieDto updateMovie(int id, MovieDto movieDto);

    /**
     * Deletes a movie.
     *
     * @param id The ID of the movie to delete
     */
    void deleteMovie(int id);
}