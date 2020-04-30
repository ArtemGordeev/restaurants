DELETE
FROM dishes;
DELETE
FROM menus;
DELETE
FROM restaurants;
DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM VOTES;
ALTER SEQUENCE GLOBAL_SEQ
RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO restaurants (title)
VALUES ('KFC');

INSERT INTO menus (title, date_time, restaurant_id)
VALUES ('Monday', '2020-04-01 00:00:00', 100002);

INSERT INTO dishes (description, price, menu_id)
VALUES ('Pizza', 150, 100003),
       ('Tea', 50, 100003);

-- INSERT INTO VOTES (date_time, restaurant_id, user_id)
-- VALUES ('2020-04-01 12:00:00', 100002, 100000),
--        ('2020-04-01 11:00:00', 100002, 100001),
--        ('2020-04-28 12:00:00', 100002, 100000),
--        ('2020-04-28 11:00:00', 100002, 100001);



