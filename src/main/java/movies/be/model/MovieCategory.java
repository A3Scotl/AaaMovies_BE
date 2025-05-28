package movies.be.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movie_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}