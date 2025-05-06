INSERT IGNORE INTO countries (id, name, active) VALUES
(1, 'Hàn Quốc', true),
(2, 'Mỹ', true),
(3, 'Nhật Bản', true),
(4, 'Trung Quốc', true),
(5, 'Việt Nam', true),
(6, 'Thái Lan', true),
(7, 'Ấn Độ', true),
(8, 'Pháp', true),
(9, 'Đức', true),
(10, 'Úc', true);

INSERT IGNORE INTO categories (id, name, active) VALUES
(1, 'Kinh Dị', true),
(2, 'Hành Động', true),
(3, 'Hài Hước', true),
(4, 'Lãng Mạn', true),
(5, 'Phiêu Lưu', true),
(6, 'Khoa Học Viễn Tưởng', true),
(7, 'Drama', true),
(8, 'Hoạt Hình', true),
(9, 'Tài Liệu', true),
(10, 'Gia Đình', true),
(11, 'Bí Ẩn', true),
(12, 'Trường Học', true),
(13, 'Chính Trị', true);

INSERT IGNORE INTO movies (movie_id, title, origin_name, type, description, thumbnail, quality, lang, status, view, country_id, release_year, is_hot, is_new, is_active, created_at, trailer_url) VALUES
(1, 'Trường Học Xác Sống', 'All of Us Are Dead', 'SERIES', 'Câu chuyện xoay quanh nhóm học sinh bị mắc kẹt trong trường học khi xảy ra dịch zombie, chiến đấu không chỉ với xác sống mà còn với lòng tham và ghen tị của con người.', 'https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg', 'HD', 'VIETSUB', 'COMPLETED', 1000, 1, 2021, true, false, true, '2025-05-05 10:00:00', ''),
(2, 'Đêm Hung Tàn', 'Violent Night', 'SINGLE', 'Khi một nhóm lính đánh thuê tấn công dinh thự của gia đình giàu có vào đêm Giáng sinh, họ không ngờ tới đối thủ bất ngờ: Ông già Noel, người sẽ chứng minh ông không phải là thánh nhân.', 'https://img.ophim.live/uploads/movies/dem-hung-tan-thumb.jpg', 'HD', 'VIETSUB', 'COMPLETED', 800, 2, 2022, true, true, true, '2025-05-05 10:00:00', ''),
(3, 'One Piece: Thành Phố Vàng', 'One Piece Film: Gold', 'SINGLE', 'Thuyền trưởng Mũ Rơm tiếp tục phiêu lưu tại Gran Tesoro, thành phố giải trí lớn nhất thế giới, đối đầu với Hoàng đế Vàng Guild Tesoro. Ông ta muốn gì và phi hành đoàn sẽ làm gì?', 'https://img.ophim.live/uploads/movies/vua-hai-tac-thanh-pho-vang-thumb.jpg', 'HD', 'VIETSUB', 'COMPLETED', 600, 3, 2016, false, false, true, '2025-05-05 10:00:00', ''),
(4, 'One Piece: Trái Tim Vàng', 'One Piece: Heart of Gold', 'SINGLE', 'Câu chuyện theo chân Myskina Olga, người bị săn đuổi bởi Chính phủ Thế giới và Mad Treasure vì biết vị trí của Vàng Nguyên Chất. Cô hợp tác với Thuyền trưởng Mũ Rơm để đến đảo Alchemi.', 'https://img.ophim.live/uploads/movies/vua-hai-tac-trai-tim-vang-thumb.jpg', 'HD', 'VIETSUB', 'COMPLETED', 550, 3, 2016, false, false, true, '2025-05-05 10:00:00', ''),
(5, 'Doraemon: Nobita Và Hành Trình Khám Phá Mặt Trăng', 'Doraemon: Nobita''s Chronicle of the Moon Exploration', 'SINGLE', 'Nobita nhìn thấy mặt trăng vàng và kể với lớp học, nhưng bị cười nhạo. Doraemon đưa anh chiếc huy hiệu “Club Club Club” và họ phát hiện sự sống trên mặt trăng.', 'https://img.ophim.live/uploads/movies/doraemon-nobita-va-mat-trang-phieu-luu-ky-thumb.jpg', 'HD', 'VIETSUB', 'COMPLETED', 500, 3, 2019, false, false, true, '2025-05-05 10:00:00', ''),
(6, 'Anh Hùng Yếu Đuối Lớp 1', 'Weak Hero Class 1', 'SERIES', 'Yeon Shi-Eun, học sinh xuất sắc nhưng trông yếu đuối, dùng trí thông minh, công cụ và tâm lý để chống lại bạo lực trong và ngoài trường học.', 'https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg', 'HD', 'VIETSUB', 'COMPLETED', 1200, 1, 2022, true, true, true, '2025-05-05 10:00:00', ''),
(7, 'Chiếc Thìa Vàng', 'The Golden Spoon', 'SERIES', 'Dựa trên truyện tranh “Geumsujeo,” kể về Lee Seung Cheon, cậu bé nghèo đổi đời với bạn giàu nhờ chiếc thìa vàng thần kỳ.', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'HD', 'VIETSUB', 'COMPLETED', 1500, 1, 2022, true, true, true, '2025-05-05 10:00:00', '');

INSERT IGNORE INTO episodes (movie_id, episode_number, title, thumbnail, video_url, duration, created_at) VALUES
(1, 1, 'Tập 1', 'https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg', 'https://vip.opstream16.com/share/7362b26d78069dd38f4b45743fddc7ee', 60, '2025-05-05 10:00:00'),
(1, 2, 'Tập 2', 'https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg', 'https://vip.opstream16.com/share/dc14e10c0ecf8029a86d27e74d140539', 60, '2025-05-05 10:00:00'),
(1, 3, 'Tập 3', 'https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg', 'https://vip.opstream16.com/share/c031d32c88833d1f9a2144071eaf34d9', 60, '2025-05-05 10:00:00'),
(1, 4, 'Tập 4', 'https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg', 'https://vip.opstream16.com/share/a2667dd894062c9ca2a4602cb4718f52', 60, '2025-05-05 10:00:00'),
(1, 5, 'Tập 5', 'https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg', 'https://vip.opstream16.com/share/a01874b1881feb4f498d3f877bd41e62', 60, '2025-05-05 10:00:00'),
(1, 6, 'Tập 6', 'https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg', 'https://vip.opstream16.com/share/070ffad3c0f12da66ca3b5d0c2d23069', 60, '2025-05-05 10:00:00'),
(1, 7, 'Tập 7', 'https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg', 'https://vip.opstream16.com/share/381dc6cd0e6bfa5feb1f70484171a7a9', 60, '2025-05-05 10:00:00'),
(1, 8, 'Tập 8', 'https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg', 'https://vip.opstream16.com/share/f6c744ece7e1a36892eba3a5d2938110', 60, '2025-05-05 10:00:00'),
(1, 9, 'Tập 9', 'https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg', 'https://vip.opstream16.com/share/992293aa502a94d9d76d1d0313c00873', 60, '2025-05-05 10:00:00'),
(1, 10, 'Tập 10', 'https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg', 'https://vip.opstream16.com/share/508cb643c0ea0e2f6451bba7aa5cfb64', 60, '2025-05-05 10:00:00'),
(1, 11, 'Tập 11', 'https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg', 'https://vip.opstream16.com/share/d61f3a760c9bcbc9bb75228deddd9379', 60, '2025-05-05 10:00:00'),
(1, 12, 'Tập 12', 'https://img.ophim.live/uploads/movies/ngoi-truong-xac-song-thumb.jpg', 'https://vip.opstream16.com/share/337751565e513506b6400ca2ad6ff5df', 60, '2025-05-05 10:00:00'),
(2, 1, 'Đêm Hung Tàn', 'https://img.ophim.live/uploads/movies/dem-hung-tan-thumb.jpg', 'https://vip.opstream16.com/share/a46e10627f49a5333cdcd0b700790f8b', 112, '2025-05-05 10:00:00'),
(3, 1, 'One Piece: Thành Phố Vàng', 'https://img.ophim.live/uploads/movies/vua-hai-tac-thanh-pho-vang-thumb.jpg', 'https://vip.opstream14.com/share/839541bfa1e1f4a879c4a5d4e5f6d88b', 120, '2025-05-05 10:00:00'),
(4, 1, 'One Piece: Trái Tim Vàng', 'https://img.ophim.live/uploads/movies/vua-hai-tac-trai-tim-vang-thumb.jpg', 'https://vip.opstream16.com/share/bb633eed6f972501da3ea6d9eb9d5e77', 104, '2025-05-05 10:00:00'),
(5, 1, 'Doraemon: Nobita Và Hành Trình Khám Phá Mặt Trăng', 'https://img.ophim.live/uploads/movies/doraemon-nobita-va-mat-trang-phieu-luu-ky-thumb.jpg', 'https://vip.opstream12.com/share/ab8df9f7733db5611fd53748f1db282e', 111, '2025-05-05 10:00:00'),
(6, 1, 'Tập 1', 'https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg', 'https://vip.opstream15.com/share/ceef9a7959adba8d6498247338ccb9e5', 45, '2025-05-05 10:00:00'),
(6, 2, 'Tập 2', 'https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg', 'https://vip.opstream16.com/share/f25a418b357e97b19bcc1a3eda1ac864', 45, '2025-05-05 10:00:00'),
(6, 3, 'Tập 3', 'https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg', 'https://vip.opstream16.com/share/d0683d8499a7dbd42ee1abbb938e090b', 45, '2025-05-05 10:00:00'),
(6, 4, 'Tập 4', 'https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg', 'https://vip.opstream16.com/share/a366cae9b72e785998b0ce78a2284bdb', 45, '2025-05-05 10:00:00'),
(6, 5, 'Tập 5', 'https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg', 'https://vip.opstream16.com/share/a2ad4a29fc99de79997f9a616c345a7b', 45, '2025-05-05 10:00:00'),
(6, 6, 'Tập 6', 'https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg', 'https://vip.opstream16.com/share/8b867815185dd36ff091a2702449feb9', 45, '2025-05-05 10:00:00'),
(6, 7, 'Tập 7', 'https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg', 'https://vip.opstream16.com/share/7b18ceae096a51a11c3f945abece6238', 45, '2025-05-05 10:00:00'),
(6, 8, 'Tập 8', 'https://img.ophim.live/uploads/movies/nguoi-hung-yeu-duoi-thumb.jpg', 'https://vip.opstream16.com/share/ce4e66f7f29c8b37eff635378a9b9c0f', 45, '2025-05-05 10:00:00'),
(7, 1, 'Tập 1', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream16.com/share/2d79ee33c5f2e4b7594fd3d3b67f36ed', 78, '2025-05-05 10:00:00'),
(7, 2, 'Tập 2', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream16.com/share/d3547395bd58fd67456dffd021f65181', 78, '2025-05-05 10:00:00'),
(7, 3, 'Tập 3', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream16.com/share/04d81fced5c98dd0927231751561d1fe', 78, '2025-05-05 10:00:00'),
(7, 4, 'Tập 4', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream15.com/share/36609c3d6cf0d6ca0887852c9d84e57d', 78, '2025-05-05 10:00:00'),
(7, 5, 'Tập 5', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream16.com/share/2965658b89b496b7c43d6a6ea3ac930b', 78, '2025-05-05 10:00:00'),
(7, 6, 'Tập 6', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream16.com/share/e674ecbaf6823de1eefc36451d5b7fb7', 78, '2025-05-05 10:00:00'),
(7, 7, 'Tập 7', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream15.com/share/aad65b962da06a412ef0d9e499960b1a', 78, '2025-05-05 10:00:00'),
(7, 8, 'Tập 8', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream15.com/share/6d55a62e8f25f61dee04192481e8fab6', 78, '2025-05-05 10:00:00'),
(7, 9, 'Tập 9', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream11.com/share/6cb5686162b6b655404343ae77bd9348', 78, '2025-05-05 10:00:00'),
(7, 10, 'Tập 10', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream15.com/share/b91f9ae1efafed4d85107226536195f3', 78, '2025-05-05 10:00:00'),
(7, 11, 'Tập 11', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream15.com/share/47dd74c3eeffe3ee49f5a49d8a82dbd8', 78, '2025-05-05 10:00:00'),
(7, 12, 'Tập 12', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream15.com/share/cd42a075f19bf80f59b7d826c6c2d43d', 78, '2025-05-05 10:00:00'),
(7, 13, 'Tập 13', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream11.com/share/8c8b4ac8a579c8bea7c977fe96d65983', 78, '2025-05-05 10:00:00'),
(7, 14, 'Tập 14', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream16.com/share/99e54f99035fe00a5b02e468dedeb7fe', 78, '2025-05-05 10:00:00'),
(7, 15, 'Tập 15', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream15.com/share/82166899aa3db47b87226f5e2abfbca7', 78, '2025-05-05 10:00:00'),
(7, 16, 'Tập 16', 'https://img.ophim.live/uploads/movies/gold-spoon-thumb.jpg', 'https://vip.opstream16.com/share/db68424c064838f1bfdd2380c06b1ee6', 78, '2025-05-05 10:00:00');

INSERT IGNORE INTO roles (name) VALUES
('ADMIN'),
('USER');

INSERT IGNORE INTO users (email, full_name, password, created_at, active) VALUES
('user1@example.com', 'Người Dùng 1', '$2a$10$FPyI.OOxn/YS3h/BibkjreLWCHO3UKgr6x1xfMSuTmEg/0NzLQ1Yq', '2025-05-05 10:00:00', true),
('user2@example.com', 'Người Dùng 2', '$2a$10$FPyI.OOxn/YS3h/BibkjreLWCHO3UKgr6x1xfMSuTmEg/0NzLQ1Yq', '2025-05-05 10:00:00', true),
('admin@gmail.com', 'Quản Trị Viên', '$2a$10$r8lQodMhhn3RX3Yr8K.5R.xsd4YaQTWodliFaUkm/bsuPjBPWe3Na', '2025-05-05 10:00:00', true);

INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(1, 2), -- Người Dùng 1 là USER
(2, 2), -- Người Dùng 2 là USER
(3, 1); -- Admin là ADMIN

INSERT IGNORE INTO movie_categories (movie_id, category_id) VALUES
(1, 1),
(1, 7),
(2, 2),
(2, 3),
(3, 2),
(3, 3),
(3, 5),
(3, 11),
(4, 3),
(4, 5),
(4, 11),
(5, 2),
(5, 3),
(5, 6),
(5, 10),
(6, 7),
(6, 12),
(7, 13),
(7, 11);