/*
 * @ (#) CountryRepository.java 1.0 5/4/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */
package movies.be.repository;

import movies.be.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * @description
 * @author : Nguyen Truong An
 * @date : 5/4/2025
 * @version 1.0
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
