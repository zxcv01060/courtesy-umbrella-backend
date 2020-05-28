INSERT INTO sharing_economy.user_role(id, name)
VALUES (1, 'admin'),
       (2, 'manager'),
       (3, 'normal'),
       (4, 'repairer');
INSERT INTO sharing_economy.user(account, password, role_id, enable, email, birthday, create_date, modify_id,
                                 modify_date)
VALUES ('admin', '$2a$10$hagQjQFpQTlChHKHzKpLT.0eabhsQNOOnJ/VJkH/e4PPRqoG55nsK', 1, 1, '10646007@ntub.edu.tw',
        '2020-03-28', '2020-03-28 11:35:33', 'admin', '2020-03-28 11:35:33'),
       ('anonymous', '123', 3, 0, 'ignore', '2020-03-15', '2020-03-15 20:14:27', 'admin', '2020-03-15 20:14:27');
INSERT INTO sharing_economy.item_type(id, name, enable, create_id, create_date, modify_id, modify_date)
VALUES (1, '雨傘', 1, 'admin', '2020-03-28 11:34:10', 'admin', '2020-03-28 11:36:52'),
       (2, '陽傘', 1, 'admin', '2020-03-28 11:34:10', 'admin', '2020-03-28 11:36:52');
