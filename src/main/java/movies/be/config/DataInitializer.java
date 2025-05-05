/*
 * @ (#) DataInitializer.java 1.0 2025-05-05
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */

package movies.be.config;

import movies.be.model.*;
import movies.be.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Initializes sample data when the application starts.
 *
 * @author Nguyen Truong An
 * @date 2025-05-05
 * @version 1.0
 */
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
        Category mystery = categoryRepository.save(Category.builder().name("Bí Ẩn").build());
        Category school = categoryRepository.save(Category.builder().name("Học Đường").build());
        Category political = categoryRepository.save(Category.builder().name("Chính Kịch").build());

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

        // Movie 3: Vua Hải Tặc: Thành Phố Vàng (Single)
        Movie onePieceGold = Movie.builder()
                .title("Vua Hải Tặc: Thành Phố Vàng")
                .type(Movie.MovieType.PHIM_LE)
                .description("\"Gran Tesoro\" là thành phố giải trí lớn nhất trên thế giới. Băng hải tặc Mũ Rơm tiếp tục cuộc phiêu lưu của mình, gặp Guild Teroso \"Hoàng đế vàng\", người có sức mạnh di chuyển chính phủ thế giới. Rốt cuộc anh ta muốn cái quái gì? Và nhóm Luffy sẽ làm gì!")
                .country(japan)
                .releaseYear(2016)
                .isHot(false)
                .isNew(false)
                .thumbnail("https://img.ophim.live/uploads/movies/vua-hai-tac-thanh-pho-vang-thumb.jpg")
                .videoUrl("https://vip.opstream14.com/share/839541bfa1e1f4a879c4a5d4e5f6d88b")
                .categories(Arrays.asList(action, comedy, adventure, mystery))
                .createdAt(LocalDateTime.now())
                .build();
        movieRepository.save(onePieceGold);

        // Movie 4: Vua Hải Tặc: Trái Tim Vàng (Single)
        Movie onePieceHeartOfGold = Movie.builder()
                .title("Vua Hải Tặc: Trái Tim Vàng")
                .type(Movie.MovieType.PHIM_LE)
                .description("Câu chuyện kể về một cô gái tên là Myskina Olga bị cả Chính Quyền Thế Giới và một người đàn ông tên Mad Treasure truy đuổi. Lí do bởi vì cô ta là người duy nhất biết vị trí của Pure Gold - một thứ có thể mua toàn bộ thế giới. Cô ta đã hợp tác với băng Mũ Rơm để tới hòn đảo Alchemi.")
                .country(japan)
                .releaseYear(2016)
                .isHot(false)
                .isNew(false)
                .thumbnail("https://img.ophim.live/uploads/movies/vua-hai-tac-trai-tim-vang-thumb.jpg")
                .videoUrl("https://vip.opstream16.com/share/bb633eed6f972501da3ea6d9eb9d5e77")
                .categories(Arrays.asList(comedy, adventure, mystery))
                .createdAt(LocalDateTime.now())
                .build();
        movieRepository.save(onePieceHeartOfGold);

        // Movie 5: Doraemon: Nobita và Mặt Trăng Phiêu Lưu Ký (Single)
        Movie doraemonMoon = Movie.builder()
                .title("Doraemon: Nobita và Mặt Trăng Phiêu Lưu Ký")
                .type(Movie.MovieType.PHIM_LE)
                .description("Nobita nhìn thấy một mặt trăng đã chuyển sang màu vàng đêm qua và nói với lớp học của mình về nó nhưng họ nghĩ thật buồn cười. Doraemon mang đến cho anh ta \"Hiệu trưởng Club Club Club\" và phát hiện ra cuộc sống trên mặt trăng.")
                .country(japan)
                .releaseYear(2019)
                .isHot(false)
                .isNew(false)
                .thumbnail("https://img.ophim.live/uploads/movies/doraemon-nobita-va-mat-trang-phieu-luu-ky-thumb.jpg")
                .videoUrl("https://vip.opstream12.com/share/ab8df9f7733db5611fd53748f1db282e")
                .categories(Arrays.asList(action, comedy, sciFi, family))
                .createdAt(LocalDateTime.now())
                .build();
        movieRepository.save(doraemonMoon);

        // Movie 6: Người Hùng Yếu Đuối (Series)
        Movie weakHero = Movie.builder()
                .title("Người Hùng Yếu Đuối")
                .type(Movie.MovieType.PHIM_BO)
                .description("Một học sinh gương mẫu, đứng đầu trường trung học của mình. Về mặt thể chất, Yeon Shi-Eun trông giống như một cậu bé yếu đuối, nhưng bằng cách sử dụng trí thông minh, công cụ và tâm lý của mình, anh ấy chiến đấu chống lại bạo lực diễn ra bên trong và bên ngoài trường học.")
                .country(korea)
                .releaseYear(2022)
                .isHot(true)
                .isNew(true)
                .thumbnail("https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg")
                .categories(Arrays.asList(drama, school))
                .createdAt(LocalDateTime.now())
                .build();
        weakHero = movieRepository.save(weakHero);

        // Episodes for Người Hùng Yếu Đuối
        episodeRepository.saveAll(Arrays.asList(
                Episode.builder()
                        .movie(weakHero)
                        .episodeNumber(1)
                        .title("Episode 1")
                        .videoUrl("https://vip.opstream15.com/share/ceef9a7959adba8d6498247338ccb9e5")
                        .thumbnail("https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg")
                        .duration(45)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(weakHero)
                        .episodeNumber(2)
                        .title("Episode 2")
                        .videoUrl("https://vip.opstream16.com/share/f25a418b357e97b19bcc1a3eda1ac864")
                        .thumbnail("https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg")
                        .duration(45)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(weakHero)
                        .episodeNumber(3)
                        .title("Episode 3")
                        .videoUrl("https://vip.opstream16.com/share/d0683d8499a7dbd42ee1abbb938e090b")
                        .thumbnail("https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg")
                        .duration(45)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(weakHero)
                        .episodeNumber(4)
                        .title("Episode 4")
                        .videoUrl("https://vip.opstream16.com/share/a366cae9b72e785998b0ce78a2284bdb")
                        .thumbnail("https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg")
                        .duration(45)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(weakHero)
                        .episodeNumber(5)
                        .title("Episode 5")
                        .videoUrl("https://vip.opstream16.com/share/a2ad4a29fc99de79997f9a616c345a7b")
                        .thumbnail("https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg")
                        .duration(45)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(weakHero)
                        .episodeNumber(6)
                        .title("Episode 6")
                        .videoUrl("https://vip.opstream16.com/share/8b867815185dd36ff091a2702449feb9")
                        .thumbnail("https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg")
                        .duration(45)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(weakHero)
                        .episodeNumber(7)
                        .title("Episode 7")
                        .videoUrl("https://vip.opstream16.com/share/7b18ceae096a51a11c3f945abece6238")
                        .thumbnail("https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg")
                        .duration(45)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(weakHero)
                        .episodeNumber(8)
                        .title("Episode 8")
                        .videoUrl("https://vip.opstream16.com/share/ce4e66f7f29c8b37eff635378a9b9c0f")
                        .thumbnail("https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg")
                        .duration(45)
                        .createdAt(LocalDateTime.now())
                        .build()
        ));

        // Movie 7: Thìa Vàng (Series)
        Movie goldenSpoon = Movie.builder()
                .title("Thìa Vàng")
                .type(Movie.MovieType.PHIM_BO)
                .description("Bộ phim truyền hình này dựa trên webcomic “Geumsujeo”. Lee Seung Cheon, một đứa trẻ sinh ra trong gia đình nghèo, thay đổi số phận với một người bạn sinh ra trong gia đình giàu có thông qua chiếc thìa vàng.")
                .country(korea)
                .releaseYear(2022)
                .isHot(true)
                .isNew(true)
                .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                .categories(Arrays.asList(political, mystery))
                .createdAt(LocalDateTime.now())
                .build();
        goldenSpoon = movieRepository.save(goldenSpoon);

        // Episodes for Thìa Vàng
        episodeRepository.saveAll(Arrays.asList(
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(1)
                        .title("Episode 1")
                        .videoUrl("https://vip.opstream16.com/share/2d79ee33c5f2e4b7594fd3d3b67f36ed")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(2)
                        .title("Episode 2")
                        .videoUrl("https://vip.opstream16.com/share/d3547395bd58fd67456dffd021f65181")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(3)
                        .title("Episode 3")
                        .videoUrl("https://vip.opstream16.com/share/04d81fced5c98dd0927231751561d1fe")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(4)
                        .title("Episode 4")
                        .videoUrl("https://vip.opstream15.com/share/36609c3d6cf0d6ca0887852c9d84e57d")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(5)
                        .title("Episode 5")
                        .videoUrl("https://vip.opstream16.com/share/2965658b89b496b7c43d6a6ea3ac930b")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(6)
                        .title("Episode 6")
                        .videoUrl("https://vip.opstream16.com/share/e674ecbaf6823de1eefc36451d5b7fb7")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(7)
                        .title("Episode 7")
                        .videoUrl("https://vip.opstream15.com/share/aad65b962da06a412ef0d9e499960b1a")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(8)
                        .title("Episode 8")
                        .videoUrl("https://vip.opstream15.com/share/6d55a62e8f25f61dee04192481e8fab6")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(9)
                        .title("Episode 9")
                        .videoUrl("https://vip.opstream11.com/share/6cb5686162b6b655404343ae77bd9348")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(10)
                        .title("Episode 10")
                        .videoUrl("https://vip.opstream15.com/share/b91f9ae1efafed4d85107226536195f3")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(11)
                        .title("Episode 11")
                        .videoUrl("https://vip.opstream15.com/share/47dd74c3eeffe3ee49f5a49d8a82dbd8")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(12)
                        .title("Episode 12")
                        .videoUrl("https://vip.opstream15.com/share/cd42a075f19bf80f59b7d826c6c2d43d")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(13)
                        .title("Episode 13")
                        .videoUrl("https://vip.opstream11.com/share/8c8b4ac8a579c8bea7c977fe96d65983")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(14)
                        .title("Episode 14")
                        .videoUrl("https://vip.opstream16.com/share/99e54f99035fe00a5b02e468dedeb7fe")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(15)
                        .title("Episode 15")
                        .videoUrl("https://vip.opstream15.com/share/82166899aa3db47b87226f5e2abfbca7")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Episode.builder()
                        .movie(goldenSpoon)
                        .episodeNumber(16)
                        .title("Episode 16")
                        .videoUrl("https://vip.opstream16.com/share/db68424c064838f1bfdd2380c06b1ee6")
                        .thumbnail("https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg")
                        .duration(78)
                        .createdAt(LocalDateTime.now())
                        .build()
        ));
    }
}