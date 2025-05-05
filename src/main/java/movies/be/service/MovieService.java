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
}