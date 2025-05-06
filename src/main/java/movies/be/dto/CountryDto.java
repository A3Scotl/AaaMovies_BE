/*
 * @ (#) CountryDto.java 1.0 5/6/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */

package movies.be.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * @description
 * @author : Nguyen Truong An
 * @date : 5/6/2025
 * @version 1.0
 */
@Getter
@Setter
public class CountryDto {
    private Long id;
    private String name;
    private boolean active;
}
