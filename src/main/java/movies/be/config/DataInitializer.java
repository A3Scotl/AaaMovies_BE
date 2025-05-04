package movies.be.config;

import movies.be.model.*;
import movies.be.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final CountryRepository countryRepository;
    private final CategoryRepository categoryRepository;
    private final EpisodeRepository episodeRepository;

    @Override
    public void run(String... args) {
        // Check if data already exists
        if (movieRepository.count() > 0) {
            return;
        }

        // Initialize countries
        Country korea = countryRepository.save(Country.builder().name("Hàn Quốc").build());
        Country usa = countryRepository.save(Country.builder().name("Âu Mỹ").build());
        Country japan = countryRepository.save(Country.builder().name("Nhật Bản").build());
        Country china = countryRepository.save(Country.builder().name("Trung Quốc").build());
        Country vietnam = countryRepository.save(Country.builder().name("Việt Nam").build());
        Country thailand = countryRepository.save(Country.builder().name("Thái Lan").build());
        Country india = countryRepository.save(Country.builder().name("Ấn Độ").build());
        Country france = countryRepository.save(Country.builder().name("Pháp").build());
        Country germany = countryRepository.save(Country.builder().name("Đức").build());
        Country australia = countryRepository.save(Country.builder().name("Úc").build());

        // Initialize categories
        Category horror = categoryRepository.save(Category.builder().name("Kinh Dị").build());
        Category action = categoryRepository.save(Category.builder().name("Hành Động").build());
        Category comedy = categoryRepository.save(Category.builder().name("Hài Hước").build());
        Category romance = categoryRepository.save(Category.builder().name("Tình Cảm").build());
        Category adventure = categoryRepository.save(Category.builder().name("Phiêu Lưu").build());
        Category sciFi = categoryRepository.save(Category.builder().name("Khoa Học Viễn Tưởng").build());
        Category drama = categoryRepository.save(Category.builder().name("Tâm Lý").build());
        Category animation = categoryRepository.save(Category.builder().name("Hoạt Hình").build());
        Category documentary = categoryRepository.save(Category.builder().name("Tài Liệu").build());
        Category family = categoryRepository.save(Category.builder().name("Gia Đình").build());

        // Movie 1: Ngôi Trường Xác Sống (Series)
        Movie allOfUsAreDead = Movie.builder()
                .title("Ngôi Trường Xác Sống")
                .type(Movie.MovieType.PHIM_BO)
                .description("Phim là câu chuyện xoay quanh hành trình sống còn chống lại xác sống của một nhóm học sinh bị mắc kẹt trong trường học khi xuất hiện một loại virus lây nhiễm có khả năng biến con người thành xác sống đang lây lan khắp thành phố. Đây không chỉ đơn thuần là cuộc đấu tranh với những thứ nguy hiểm và đáng sợ mà còn là thử thách khi đối diện với sự đố kỵ và lòng tham vô đáy của con người.")
                .country(korea)
                .releaseYear(2021)
                .isHot(true)
                .isNew(false)
                .thumbnail("https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg")
                .categories(Arrays.asList(horror, drama))
                .createdAt(LocalDateTime.now())
                .build();
        allOfUsAreDead = movieRepository.save(allOfUsAreDead);

        // Episodes for Ngôi Trường Xác Sống
        episodeRepository.saveAll(Arrays.asList(
                Episode.builder()
                        .movie(allOfUsAreDead)
                        .episodeNumber(1)
                        .title("Episode 1")
                        .videoUrl("https://vip.opstream16.com/share/7362b26d78069dd38f4b45743fddc7ee")
                        .thumbnail("https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg")
                        .duration(60)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(allOfUsAreDead)
                        .episodeNumber(2)
                        .title("Episode 2")
                        .videoUrl("https://vip.opstream16.com/share/dc14e10c0ecf8029a86d27e74d140539")
                        .thumbnail("https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg")
                        .duration(60)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(allOfUsAreDead)
                        .episodeNumber(3)
                        .title("Episode 3")
                        .videoUrl("https://vip.opstream16.com/share/c031d32c88833d1f9a2144071eaf34d9")
                        .thumbnail("https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg")
                        .duration(60)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(allOfUsAreDead)
                        .episodeNumber(4)
                        .title("Episode 4")
                        .videoUrl("https://vip.opstream16.com/share/a2667dd894062c9ca2a4602cb4718f52")
                        .thumbnail("https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg")
                        .duration(60)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(allOfUsAreDead)
                        .episodeNumber(5)
                        .title("Episode 5")
                        .videoUrl("https://vip.opstream16.com/share/a01874b1881feb4f498d3f877bd41e62")
                        .thumbnail("https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg")
                        .duration(60)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(allOfUsAreDead)
                        .episodeNumber(6)
                        .title("Episode 6")
                        .videoUrl("https://vip.opstream16.com/share/070ffad3c0f12da66ca3b5d0c2d23069")
                        .thumbnail("https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg")
                        .duration(60)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(allOfUsAreDead)
                        .episodeNumber(7)
                        .title("Episode 7")
                        .videoUrl("https://vip.opstream16.com/share/381dc6cd0e6bfa5feb1f70484171a7a9")
                        .thumbnail("https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg")
                        .duration(60)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(allOfUsAreDead)
                        .episodeNumber(8)
                        .title("Episode 8")
                        .videoUrl("https://vip.opstream16.com/share/f6c744ece7e1a36892eba3a5d2938110")
                        .thumbnail("https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg")
                        .duration(60)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(allOfUsAreDead)
                        .episodeNumber(9)
                        .title("Episode 9")
                        .videoUrl("https://vip.opstream16.com/share/992293aa502a94d9d76d1d0313c00873")
                        .thumbnail("https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg")
                        .duration(60)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(allOfUsAreDead)
                        .episodeNumber(10)
                        .title("Episode 10")
                        .videoUrl("https://vip.opstream16.com/share/508cb643c0ea0e2f6451bba7aa5cfb64")
                        .thumbnail("https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg")
                        .duration(60)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(allOfUsAreDead)
                        .episodeNumber(11)
                        .title("Episode 11")
                        .videoUrl("https://vip.opstream16.com/share/d61f3a760c9bcbc9bb75228deddd9379")
                        .thumbnail("https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg")
                        .duration(60)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(allOfUsAreDead)
                        .episodeNumber(12)
                        .title("Episode 12")
                        .videoUrl("https://vip.opstream16.com/share/337751565e513506b6400ca2ad6ff5df")
                        .thumbnail("https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg")
                        .duration(60)
                        .createdAt(LocalDateTime.now())
                        .build()
        ));

        // Movie 2: Đêm Hung Tàn (Single)
        Movie violentNight = Movie.builder()
                .title("Đêm Hung Tàn")
                .type(Movie.MovieType.PHIM_LE)
                .description("Khi một nhóm lính đánh thuê đột nhập vào khu nhà của một gia đình giàu có vào đêm Giáng sinh, bắt mọi người bên trong làm con tin, nhóm đã không chuẩn bị cho một chiến binh bất ngờ: Santa Claus đang ở trong căn cứ, và anh ấy sắp cho thấy tại sao Nick này không phải là thánh.")
                .country(usa)
                .releaseYear(2022)
                .isHot(true)
                .isNew(true)
                .thumbnail("https://img.ophim.live/uploads/movies/dem-hung-tan-thumb.jpg")
                .videoUrl("https://vip.opstream16.com/share/a46e10627f49a5333cdcd0b700790f8b")
                .categories(Arrays.asList(action, comedy))
                .createdAt(LocalDateTime.now())
                .build();
        movieRepository.save(violentNight);
    }
}