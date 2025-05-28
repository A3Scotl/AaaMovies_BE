package movies.be.repository;

import movies.be.model.MovieCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieCategoryRepository extends JpaRepository<MovieCategory, Long> {
    List<MovieCategory> findByCategoryId(Long categoryId);
}