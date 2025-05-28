package movies.be.service.impl;

import movies.be.dto.EpisodeDto;
import movies.be.dto.MovieDto;
import movies.be.dto.RatingDto;
import movies.be.exception.ErrorMessages;
import movies.be.exception.MovieException;
import movies.be.model.*;
import movies.be.repository.CategoryRepository;
import movies.be.repository.MovieCategoryRepository;
import movies.be.repository.MovieRepository;
import movies.be.repository.UserRepository;
import movies.be.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final MovieCategoryRepository movieCategoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, CategoryRepository categoryRepository,
                            MovieCategoryRepository movieCategoryRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
        this.movieCategoryRepository = movieCategoryRepository;
        this.userRepository = userRepository;
    }

    private MovieDto convertToDto(Movie movie) {
        MovieDto dto = new MovieDto();
        dto.setMovieId(movie.getMovieId());
        dto.setTitle(movie.getTitle());
        dto.setOriginName(movie.getOriginName());
        dto.setType(movie.getType().toString());
        dto.setDescription(movie.getDescription());
        dto.setThumbnail(movie.getThumbnail());
        dto.setQuality(movie.getQuality() != null ? movie.getQuality().toString() : null);
        dto.setLang(movie.getLang() != null ? movie.getLang().toString() : null);
        dto.setStatus(movie.getStatus() != null ? movie.getStatus().toString() : null);
        dto.setView(movie.getView());
        dto.setCountryId(movie.getCountry() != null ? movie.getCountry().getId() : null);
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setHot(movie.isHot());
        dto.setNew(movie.isNew());
        dto.setActive(movie.isActive());
        dto.setCreatedAt(movie.getCreatedAt());
        dto.setTrailerUrl(movie.getTrailerUrl());
        if (movie.getEpisodes() != null) {
            List<EpisodeDto> episodeDtos = movie.getEpisodes().stream()
                    .map(this::convertEpisodeToDto)
                    .collect(Collectors.toList());
            dto.setEpisodes(episodeDtos);
            dto.setEpisodeTotal(episodeDtos.size());
        }
        if (movie.getRatings() != null && !movie.getRatings().isEmpty()) {
            double avgRating = movie.getRatings().stream()
                    .mapToDouble(Rating::getRatingValue)
                    .average()
                    .orElse(0.0);
            dto.setAverageRating(avgRating);
        } else {
            dto.setAverageRating(0.0);
        }
        if (movie.getMovieCategories() != null) {
            dto.setCategoryIds(movie.getMovieCategories().stream()
                    .map(movieCategory -> movieCategory.getCategory().getId())
                    .collect(Collectors.toList()));
        }
        return dto;
    }

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

    private Movie convertToEntity(MovieDto dto) {
        Movie movie = new Movie();
        movie.setMovieId(dto.getMovieId());
        movie.setTitle(dto.getTitle());
        movie.setOriginName(dto.getOriginName());
        movie.setType(MovieType.valueOf(dto.getType()));
        movie.setDescription(dto.getDescription());
        movie.setThumbnail(dto.getThumbnail());
        movie.setQuality(dto.getQuality() != null ? MovieQuality.valueOf(dto.getQuality()) : null);
        movie.setLang(dto.getLang() != null ? MovieLang.valueOf(dto.getLang()) : null);
        movie.setStatus(dto.getStatus() != null ? MovieStatus.valueOf(dto.getStatus()) : null);
        movie.setView(dto.getView());
        if (dto.getCountryId() != null) {
            Country country = new Country();
            country.setId(dto.getCountryId());
            movie.setCountry(country);
        }
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setHot(dto.isHot());
        movie.setNew(dto.isNew());
        movie.setActive(dto.isActive());
        movie.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        movie.setTrailerUrl(dto.getTrailerUrl() != null ? dto.getTrailerUrl() : "");
        if (dto.getEpisodes() != null) {
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

    private void validateMovieData(MovieDto movieDto) {
        if (movieDto == null || movieDto.getTitle() == null || movieDto.getTitle().trim().isEmpty()) {
            throw new MovieException(ErrorMessages.INVALID_MOVIE_DATA_MESSAGE);
        }
        if (movieDto.getType() == null || (!"SERIES".equals(movieDto.getType()) && !"SINGLE".equals(movieDto.getType()))) {
            throw new MovieException("Movie type must be SERIES or SINGLE");
        }
        if (movieDto.getStatus() == null || (!"UPCOMING".equals(movieDto.getStatus()) && !"ONGOING".equals(movieDto.getStatus()) && !"COMPLETED".equals(movieDto.getStatus()))) {
            throw new MovieException("Movie status must be UPCOMING, ONGOING, or COMPLETED");
        }
        if (movieDto.getQuality() != null && (!"SD".equals(movieDto.getQuality()) && !"HD".equals(movieDto.getQuality()))) {
            throw new MovieException("Movie quality must be SD or HD");
        }
        if (movieDto.getLang() != null && (!"VIETSUB".equals(movieDto.getLang()) && !"THUYET_MINH".equals(movieDto.getLang()))) {
            throw new MovieException("Movie lang must be VIETSUB or THUYET_MINH");
        }
        if (movieDto.getEpisodes() == null || movieDto.getEpisodes().isEmpty()) {
            throw new MovieException("Both SERIES and SINGLE must have at least one episode");
        }
        if ("SINGLE".equals(movieDto.getType())) {
            if (movieDto.getEpisodes().size() != 1) {
                throw new MovieException("SINGLE must have exactly one episode");
            }
            if (movieDto.getEpisodes().get(0).getVideoUrl() == null || movieDto.getEpisodes().get(0).getVideoUrl().trim().isEmpty()) {
                throw new MovieException("SINGLE episode must have a video URL");
            }
        } else if ("SERIES".equals(movieDto.getType())) {
            for (EpisodeDto episode : movieDto.getEpisodes()) {
                if (episode.getVideoUrl() == null || episode.getVideoUrl().trim().isEmpty()) {
                    throw new MovieException("SERIES episodes must have a video URL");
                }
            }
        }
    }

    @Override
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDto getMovieById(Long id) {
        return movieRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new MovieException(String.format(ErrorMessages.MOVIE_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public MovieDto createMovie(MovieDto movieDto) {
        validateMovieData(movieDto);
        Movie movie = convertToEntity(movieDto);
        Movie savedMovie = movieRepository.save(movie);
        if (movieDto.getCategoryIds() != null) {
            List<MovieCategory> movieCategories = movieDto.getCategoryIds().stream()
                    .map(categoryId -> {
                        Category category = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new MovieException("Category not found with id: " + categoryId));
                        return MovieCategory.builder()
                                .movie(savedMovie)
                                .category(category)
                                .build();
                    })
                    .collect(Collectors.toList());
            movieCategoryRepository.saveAll(movieCategories);
        }
        return convertToDto(savedMovie);
    }

    @Override
    public MovieDto updateMovie(Long id, MovieDto movieDto) {
        validateMovieData(movieDto);
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieException(String.format(ErrorMessages.MOVIE_NOT_FOUND_MESSAGE, id)));
        movie.setTitle(movieDto.getTitle());
        movie.setOriginName(movieDto.getOriginName());
        movie.setType(MovieType.valueOf(movieDto.getType()));
        movie.setDescription(movieDto.getDescription());
        movie.setThumbnail(movieDto.getThumbnail());
        movie.setQuality(movieDto.getQuality() != null ? MovieQuality.valueOf(movieDto.getQuality()) : null);
        movie.setLang(movieDto.getLang() != null ? MovieLang.valueOf(movieDto.getLang()) : null);
        movie.setStatus(movieDto.getStatus() != null ? MovieStatus.valueOf(movieDto.getStatus()) : null);
        movie.setView(movieDto.getView());
        if (movieDto.getCountryId() != null) {
            Country country = new Country();
            country.setId(movieDto.getCountryId());
            movie.setCountry(country);
        }
        movie.setReleaseYear(movieDto.getReleaseYear());
        movie.setHot(movieDto.isHot());
        movie.setNew(movieDto.isNew());
        movie.setActive(movieDto.isActive());
        movie.setTrailerUrl(movieDto.getTrailerUrl() != null ? movieDto.getTrailerUrl() : "");
        movie.getEpisodes().clear();
        movie.getEpisodes().addAll(movieDto.getEpisodes().stream()
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
                .collect(Collectors.toList()));
        movie.getMovieCategories().clear();
        if (movieDto.getCategoryIds() != null) {
            List<MovieCategory> movieCategories = movieDto.getCategoryIds().stream()
                    .map(categoryId -> {
                        Category category = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new MovieException("Category not found with id: " + categoryId));
                        return MovieCategory.builder()
                                .movie(movie)
                                .category(category)
                                .build();
                    })
                    .collect(Collectors.toList());
            movie.getMovieCategories().addAll(movieCategories);
        }
        return convertToDto(movieRepository.save(movie));
    }

    @Override
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new MovieException(String.format(ErrorMessages.MOVIE_NOT_FOUND_MESSAGE, id));
        }
        movieRepository.deleteById(id);
    }

    @Override
    public MovieDto addEpisodeToMovie(Long movieId, EpisodeDto episodeDto) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieException(String.format(ErrorMessages.MOVIE_NOT_FOUND_MESSAGE, movieId)));
        Episode episode = new Episode();
        episode.setEpisodeNumber(episodeDto.getEpisodeNumber());
        episode.setTitle(episodeDto.getTitle());
        episode.setThumbnail(episodeDto.getThumbnail());
        episode.setVideoUrl(episodeDto.getVideoUrl());
        episode.setDuration(episodeDto.getDuration());
        episode.setCreatedAt(episodeDto.getCreatedAt() != null ? episodeDto.getCreatedAt() : LocalDateTime.now());
        episode.setMovie(movie);
        movie.getEpisodes().add(episode);
        return convertToDto(movieRepository.save(movie));
    }

    @Override
    public MovieDto changeActive(Long id, Boolean active) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieException(String.format(ErrorMessages.MOVIE_NOT_FOUND_MESSAGE, id)));
        movie.setActive(active);
        Movie updatedMovie = movieRepository.save(movie);
        return convertToDto(updatedMovie);
    }

    @Override
    public List<MovieDto> getHotMovies() {
        return movieRepository.findByIsHotTrue().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> getNewMovies() {
        return movieRepository.findByIsNewTrue().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> getSeriesMovies() {
        return movieRepository.findByType(MovieType.SERIES).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> getSingleMovies() {
        return movieRepository.findByType(MovieType.SINGLE).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EpisodeDto> getEpisodesByMovieId(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieException(String.format(ErrorMessages.MOVIE_NOT_FOUND_MESSAGE, movieId)));
        return movie.getEpisodes().stream()
                .map(this::convertEpisodeToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> getTopRatedMovies() {
        return movieRepository.findTopRatedMovies(PageRequest.of(0, 10)).getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> getPopularMovies() {
        return movieRepository.findAllByOrderByViewDesc(PageRequest.of(0, 10)).getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> getMoviesByCountry(Long countryId) {
        return movieRepository.findByCountryId(countryId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> getMoviesByCategory(Long categoryId) {
        return movieCategoryRepository.findByCategoryId(categoryId).stream()
                .map(movieCategory -> convertToDto(movieCategory.getMovie()))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> searchMovies(String query) {
        return movieRepository.searchMovies(query).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RatingDto convertToRatingDto(Rating rating) {
        RatingDto dto = new RatingDto();
        dto.setRatingId(rating.getRatingId());
        dto.setMovieId(rating.getMovie().getMovieId());
        dto.setUserId(rating.getUser().getId());
        dto.setRatingValue(rating.getRatingValue());
        dto.setComment(rating.getComment());
        dto.setCreatedAt(rating.getCreatedAt());
        return dto;
    }

    private void validateRatingData(RatingDto ratingDto, Long movieId) {
        if (ratingDto == null || ratingDto.getUserId() == null || ratingDto.getRatingValue() == null) {
            throw new MovieException("Rating data is invalid");
        }
        if (ratingDto.getRatingValue() < 0 || ratingDto.getRatingValue() > 10) {
            throw new MovieException("Rating value must be between 0 and 10");
        }
        if (movieRepository.findById(movieId).isEmpty()) {
            throw new MovieException(String.format(ErrorMessages.MOVIE_NOT_FOUND_MESSAGE, movieId));
        }
        if (userRepository.findById(ratingDto.getUserId()).isEmpty()) {
            throw new MovieException("User not found");
        }
    }

    @Override
    public RatingDto addRating(Long movieId, RatingDto ratingDto) {
        validateRatingData(ratingDto, movieId);
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieException(String.format(ErrorMessages.MOVIE_NOT_FOUND_MESSAGE, movieId)));
        User user = userRepository.findById(ratingDto.getUserId())
                .orElseThrow(() -> new MovieException("User not found"));
        Rating rating = Rating.builder()
                .movie(movie)
                .user(user)
                .ratingValue(ratingDto.getRatingValue())
                .comment(ratingDto.getComment())
                .createdAt(ratingDto.getCreatedAt() != null ? ratingDto.getCreatedAt() : LocalDateTime.now())
                .build();
        movie.getRatings().add(rating);
        movieRepository.save(movie);
        return convertToRatingDto(rating);
    }

    @Override
    public List<RatingDto> getRatingsByMovieId(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieException(String.format(ErrorMessages.MOVIE_NOT_FOUND_MESSAGE, movieId)));
        return movie.getRatings().stream()
                .map(this::convertToRatingDto)
                .collect(Collectors.toList());
    }
}