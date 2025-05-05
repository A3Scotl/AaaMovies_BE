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
    private String originName;
    private String type;
    private String description;
    private String thumbnail;
    private String quality;
    private String lang;
    private String status;
    private Long view;
    private Long countryId;
    private Integer releaseYear;
    private boolean isHot;
    private boolean isNew;
    private LocalDateTime createdAt;
    private String trailerUrl;
    private List<EpisodeDto> episodes;
    private List<Long> categoryIds;
    private Double averageRating;
    private Integer episodeTotal;
}