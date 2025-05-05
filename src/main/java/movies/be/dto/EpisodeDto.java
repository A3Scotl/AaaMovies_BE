/*
 * @ (#) EpisodeDto.java 1.0 2025-05-05
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */

package movies.be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Episode entity.
 *
 * @author Nguyen Truong An
 * @date 2025-05-05
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeDto {

    private Long episodeId;

    private Integer episodeNumber;

    private String title;

    private String thumbnail;

    private String videoUrl;

    private Integer duration;

    private LocalDateTime createdAt;
}