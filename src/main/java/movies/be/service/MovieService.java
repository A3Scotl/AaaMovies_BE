package movies.be.service;

import movies.be.dto.EpisodeDto;
import movies.be.dto.MovieDto;

import java.util.List;

public interface MovieService {
    List<MovieDto> getAllMovies();
    MovieDto getMovieById(Long id);
    MovieDto createMovie(MovieDto movieDto);
    MovieDto updateMovie(Long id, MovieDto movieDto);
    void deleteMovie(Long id);
    MovieDto addEpisodeToMovie(Long movieId, EpisodeDto episodeDto);
    MovieDto changeActive(Long id, Boolean active);
    List<MovieDto> getHotMovies();
    List<MovieDto> getNewMovies();
    List<MovieDto> getSeriesMovies();
    List<MovieDto> getSingleMovies();
    List<EpisodeDto> getEpisodesByMovieId(Long movieId);
    List<MovieDto> getTopRatedMovies();
    List<MovieDto> getPopularMovies();
    List<MovieDto> getMoviesByCountry(Long countryId);
    List<MovieDto> getMoviesByCategory(Long categoryId);
    List<MovieDto> searchMovies(String query);
}