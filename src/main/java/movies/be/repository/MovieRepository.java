package movies.be.repository;

import movies.be.model.Movie;
import movies.be.model.MovieType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByIsHotTrue();

    List<Movie> findByIsNewTrue();

    List<Movie> findByType(MovieType type);

    List<Movie> findByCountryId(Long countryId);

    @Query("SELECT DISTINCT m FROM Movie m JOIN m.categories c WHERE c.id = :categoryId")
    List<Movie> findByCategoryId(Long categoryId);

    @Query("SELECT m FROM Movie m LEFT JOIN m.ratings r GROUP BY m.movieId HAVING COUNT(r) > 0 ORDER BY AVG(r.ratingValue) DESC")
    Page<Movie> findTopRatedMovies(Pageable pageable);

    Page<Movie> findAllByOrderByViewDesc(Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR (m.originName IS NOT NULL AND LOWER(m.originName) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Movie> searchMovies(String query);

    List<Movie> findByIsActiveTrue();
}