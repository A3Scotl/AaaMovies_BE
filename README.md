AaaMovies Backend
A Spring Boot backend for a movie streaming application, featuring JWT authentication, role-based authorization, and a MariaDB database with JPA.
Features

User registration and login with JWT.
Browse movies (hot, new, series, singles).
Rate movies (users only).
Manage movies, episodes, and categories (admin only).
Optimized database with indexes.
Automatic data initialization with 10 countries, 10 categories, and 2 sample movies (1 series: Ngôi Trường Xác Sống with 12 episodes, 1 single: Đêm Hung Tàn) on startup if the database is empty.

Project Structure

movies.be.model: JPA entities (Movie, Episode, Country, Category, Rating).
movies.be.dto: Data Transfer Objects for API requests/responses.
movies.be.repository: JPA repositories for database access.
movies.be.service: Business logic (AuthService, MovieService).
movies.be.controller: REST controllers (AuthController, MovieController, AdminController).
movies.be.security: JWT and Spring Security configuration.
movies.be.config: Configuration for data initialization (DataInitializer).

Prerequisites

Java 17
MariaDB
Maven

Database Setup

Create the database with UTF-8 support:
mysql -u root -p < init_database.sql

This creates the movie_db database and tables (countries, categories, movies, episodes, movie_categories, ratings) with utf8mb4_unicode_ci collation to support Vietnamese characters.

Verify database collation:
SHOW VARIABLES LIKE 'character_set%';
SHOW VARIABLES LIKE 'collation%';

Ensure character_set_database is utf8mb4 and collation_database is utf8mb4_unicode_ci.


Application Setup

Clone the repository:git clone <repository-url>
cd AaaMovies_BE


Configure application.properties with your MariaDB credentials and JWT secret:spring.datasource.url=jdbc:mariadb://localhost:3306/movie_db?createDatabaseIfNotExist=true&characterEncoding=UTF-8&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password


Build and run:mvn spring-boot:run


On first startup, the application will populate the database with:
10 countries: Hàn Quốc, Âu Mỹ, Nhật Bản, Trung Quốc, Việt Nam, Thái Lan, Ấn Độ, Pháp, Đức, Úc.
10 categories: Kinh Dị, Hành Động, Hài Hước, Tình Cảm, Phiêu Lưu, Khoa Học Viễn Tưởng, Tâm Lý, Hoạt Hình, Tài Liệu, Gia Đình.
2 movies: Ngôi Trường Xác Sống (series, 12 episodes) and Đêm Hung Tàn (single).



API Endpoints

POST /api/auth/register: Register a new user.
POST /api/auth/login: Login and receive JWT.
GET /api/movies/hot: List hot movies (requires JWT).
GET /api/movies/new: List new movies (requires JWT).
GET /api/movies/series: List series (requires JWT).
GET /api/movies/singles: List single movies (requires JWT).
GET /api/movies/{movieId}/episodes: List episodes of a movie (requires JWT).
POST /api/movies/{movieId}/ratings: Add a rating (requires JWT, user role).
POST /api/admin/movies: Add a new movie (requires JWT, admin role).
POST /api/admin/movies/{movieId}/episodes: Add an episode (requires JWT, admin role).

Technologies

Spring Boot 3.4.5
Spring Security with JWT
JPA/Hibernate
MariaDB
Lombok
JJWT 0.12.6

Notes

The sample data in DataInitializer.java is hard-coded based on JSON from ophim1.com API (https://ophim1.com/phim/ngoi-truong-xac-song and https://ophim1.com/phim/dem-hung-tan).
Check ophim1.com's terms of use to ensure compliance when using their API or data.
To add more movies, update DataInitializer.java with data from ophim1.com API (e.g., https://ophim1.com/danh-sach/phim-moi-cap-nhat?page=1).
Ensure MariaDB uses utf8mb4_unicode_ci to support Vietnamese characters. Run init_database.sql before starting the application.

