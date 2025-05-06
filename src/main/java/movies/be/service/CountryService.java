/*
 * @ (#) CountryService.java 1.0 5/6/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */
package movies.be.service;

import movies.be.dto.CountryDto;

import java.util.List;

/*
 * @description
 * @author : Nguyen Truong An
 * @date : 5/6/2025
 * @version 1.0
 */
public interface CountryService {
    List<CountryDto> getAllCountries();
    CountryDto getCountryById(Long id);
    CountryDto createCountry(CountryDto CountryDto);
    CountryDto updateCountry(Long id, CountryDto CountryDto);
    void deleteCountry(Long id);
}
