CREATE DATABASE IF NOT EXISTS movie_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE movie_db;

ALTER DATABASE movie_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS countries (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS categories (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS movies (
                                      movie_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      title VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    type ENUM('PHIM_BO', 'PHIM_LE') NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    thumbnail VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    video_url VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    country_id BIGINT,
    release_year INT,
    is_hot BOOLEAN DEFAULT FALSE,
    is_new BOOLEAN DEFAULT FALSE,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (country_id) REFERENCES countries(id),
    INDEX idx_type (type),
    INDEX idx_hot (is_hot),
    INDEX idx_new (is_new),
    INDEX idx_country (country_id),
    INDEX idx_type_hot (type, is_hot)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS episodes (
                                        episode_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        movie_id BIGINT NOT NULL,
                                        episode_number INT NOT NULL,
                                        title VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    thumbnail VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    video_url VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    duration INT,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    INDEX idx_movie_id (movie_id),
    INDEX idx_episode_number (episode_number)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS movie_categories (
                                                movie_id BIGINT NOT NULL,
                                                category_id BIGINT NOT NULL,
                                                PRIMARY KEY (movie_id, category_id),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS ratings (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       movie_id BIGINT NOT NULL,
                                       user_id BIGINT NOT NULL,
                                       rating INT NOT NULL,
                                       created_at DATETIME NOT NULL,
                                       FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    INDEX idx_movie_id (movie_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;