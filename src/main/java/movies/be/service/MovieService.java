package movies.be.service;

import movies.be.dto.EpisodeDto;
import movies.be.dto.MovieDto;

import java.util.List;

public interface MovieService {
    List<MovieDto> getAllMovies();
    MovieDto getMovieById(int id);
    MovieDto createMovie(MovieDto movieDto);
    MovieDto updateMovie(int id, MovieDto movieDto);
    void deleteMovie(int id);
    MovieDto addEpisodeToMovie(int movieId, EpisodeDto episodeDto);
}