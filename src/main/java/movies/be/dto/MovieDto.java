/*
 * @ (#) MovieDto.java 1.0 2025-05-05
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */

package movies.be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private Long movieId;

    private String title;

    private String type;

    private String description;

    private String thumbnail;

    private String videoUrl;

    private Integer duration;

    private Long countryId;

    private Integer releaseYear;

    private boolean isHot;

    private boolean isNew;

    private LocalDateTime createdAt;

    private List<EpisodeDto> episodes;

    private List<Long> categoryIds;

    private Double averageRating;
}