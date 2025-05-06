/*
 * @ (#) CountryController.java 1.0 5/5/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */

package movies.be.controller;

import lombok.RequiredArgsConstructor;
import movies.be.dto.CountryDto;
import movies.be.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @description
 * @author : Nguyen Truong An
 * @date : 5/5/2025
 * @version 1.0
 */
@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        logger.info("Received request to fetch all Countries");
        List<CountryDto> Countries = countryService.getAllCountries();
        logger.debug("Successfully retrieved {} Countries", Countries.size());
        return ResponseEntity.ok(Countries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable Long id) {
        logger.info("Received request to fetch category with ID: {}", id);
        CountryDto category = countryService.getCountryById(id);
        logger.debug("Successfully retrieved category with ID: {}", id);
        return ResponseEntity.ok(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CountryDto> createCategory(@RequestBody CountryDto CountryDto) {
        logger.info("Received request to create category: {}", CountryDto.getName());
        CountryDto createdCountry = countryService.createCountry(CountryDto);
        logger.debug("Successfully created category with ID: {}", createdCountry.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCountry);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CountryDto> updateCategory(@PathVariable Long id, @RequestBody CountryDto CountryDto) {
        logger.info("Received request to update category with ID: {}", id);
        CountryDto updateCountry = countryService.updateCountry(id, CountryDto);
        logger.debug("Successfully updated category with ID: {}", id);
        return ResponseEntity.ok(updateCountry);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        logger.info("Received request to delete category with ID: {}", id);
        countryService.deleteCountry(id);
        logger.debug("Successfully deleted category with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
