/*
 * @ (#) CategoryRepository.java 1.0 5/4/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */
package movies.be.repository;

import movies.be.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
