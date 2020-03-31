INSERT INTO item_type
VALUES (1, '雨傘', 1, 'admin', '2020-03-28 11:34:10', 'admin', '2020-03-28 11:36:52'),
       (2, '陽傘', 1, 'admin', '2020-03-28 11:34:10', 'admin', '2020-03-28 11:36:52');

INSERT INTO user_role
VALUES (1, 'admin'),
       (2, 'manager'),
       (3, 'normal');

INSERT INTO `user`
VALUES ('admin', '$2a$10$hagQjQFpQTlChHKHzKpLT.0eabhsQNOOnJ/VJkH/e4PPRqoG55nsK', 1, 1, '10646007@ntub.edu.tw',
        '2020-03-28', '2020-03-28 11:35:33', '2020-03-28 11:35:33'),
       ('anonymous', '123', 3, 0, 'ignore', '2020-03-15', '2020-03-15 20:14:27', '2020-03-15 20:14:27');