/*
 * @ (#) CountryServiceImpl.java 1.0 5/6/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */

package movies.be.service.impl;

import movies.be.dto.CountryDto;
import movies.be.exception.ErrorMessages;
import movies.be.exception.MovieException;
import movies.be.model.Country;
import movies.be.repository.CountryRepository;
import movies.be.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/*
 * @description
 * @author : Nguyen Truong An
 * @date : 5/6/2025
 * @version 1.0
 */
@Service
@Transactional
public class CountryServiceImpl implements CountryService {
    private static final Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);

    private final CountryRepository CountryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository CountryRepository) {
        this.CountryRepository = CountryRepository;
    }

    private CountryDto convertToDto(Country Country) {
        CountryDto dto = new CountryDto();
        dto.setId(Country.getId());
        dto.setName(Country.getName());
        dto.setActive(Country.isActive());
        return dto;
    }

    private Country convertToEntity(CountryDto dto) {
        return Country.builder()
                .id(dto.getId())
                .name(dto.getName())
                .active(dto.isActive())
                .build();
    }

    private void validateCountryData(CountryDto CountryDto) {
        if (CountryDto == null || CountryDto.getName() == null || CountryDto.getName().trim().isEmpty()) {
            throw new MovieException(ErrorMessages.INVALID_COUNTRY_DATA_MESSAGE);
        }
    }

    @Override
    public List<CountryDto> getAllCountries() {
        logger.info("Fetching all Countries");
        return CountryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CountryDto getCountryById(Long id) {
        logger.info("Fetching Country with ID: {}", id);
        return CountryRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new MovieException(String.format(ErrorMessages.COUNTRY_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public CountryDto createCountry(CountryDto CountryDto) {
        logger.info("Creating new Country: {}", CountryDto.getName());
        validateCountryData(CountryDto);

        if (CountryRepository.existsByName(CountryDto.getName())) {
            throw new MovieException(String.format(ErrorMessages.COUNTRY_ALREADY_EXISTS_MESSAGE, CountryDto.getName()));
        }

        Country Country = convertToEntity(CountryDto);
        Country savedCountry = CountryRepository.save(Country);
        logger.info("Country created successfully with ID: {}", savedCountry.getId());
        return convertToDto(savedCountry);
    }

    @Override
    public CountryDto updateCountry(Long id, CountryDto CountryDto) {
        logger.info("Updating Country with ID: {}", id);
        validateCountryData(CountryDto);

        Country existingCountry = CountryRepository.findById(id)
                .orElseThrow(() -> new MovieException(String.format(ErrorMessages.COUNTRY_NOT_FOUND_MESSAGE, id)));

        if (!existingCountry.getName().equals(CountryDto.getName()) && CountryRepository.existsByName(CountryDto.getName())) {
            throw new MovieException(String.format(ErrorMessages.COUNTRY_ALREADY_EXISTS_MESSAGE, CountryDto.getName()));
        }

        existingCountry.setName(CountryDto.getName());
        existingCountry.setActive(CountryDto.isActive());
        Country updatedCountry = CountryRepository.save(existingCountry);
        logger.info("Country updated successfully with ID: {}", updatedCountry.getId());
        return convertToDto(updatedCountry);
    }

    @Override
    public void deleteCountry(Long id) {
        logger.info("Deleting Country with ID: {}", id);
        if (!CountryRepository.existsById(id)) {
            throw new MovieException(String.format(ErrorMessages.COUNTRY_NOT_FOUND_MESSAGE, id));
        }
        CountryRepository.deleteById(id);
        logger.info("Country deleted successfully with ID: {}", id);
    }
}
