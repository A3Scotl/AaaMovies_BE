package movies.be.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "movies", indexes = {
        @Index(name = "idx_type", columnList = "type"),
        @Index(name = "idx_hot", columnList = "is_hot"),
        @Index(name = "idx_new", columnList = "is_new"),
        @Index(name = "idx_country", columnList = "country_id"),
        @Index(name = "idx_type_hot", columnList = "type,is_hot")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String title;

    @Column(name = "origin_name", columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String originName;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MovieType type;

    @Column(name = "description", columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String description;

    @Column(name = "thumbnail", columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String thumbnail;
    @Column(name = "poster", columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String poster;

    @Enumerated(EnumType.STRING)
    @Column(name = "quality")
    private MovieQuality quality;

    @Enumerated(EnumType.STRING)
    @Column(name = "lang")
    private MovieLang lang;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MovieStatus status;

    @Column(name = "view")
    private Long view;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "is_hot")
    private boolean isHot = false;

    @Column(name = "is_new")
    private boolean isNew = false;

    @Column(name = "is_active")
    @NotNull
    private boolean isActive = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "trailer_url", columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String trailerUrl = "";

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Episode> episodes;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieCategory> movieCategories;
}