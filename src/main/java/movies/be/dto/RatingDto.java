package movies.be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {
    private Long ratingId;
    private Long movieId;
    private Long userId;
    private Double ratingValue;
    private String comment;
    private LocalDateTime createdAt;
}