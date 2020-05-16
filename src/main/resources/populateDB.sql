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
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('User2', 'user2@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('ADMIN', 100002);

INSERT INTO restaurants (title)
VALUES ('KFC'),
       ('Burger King');

INSERT INTO menus (title, date, restaurant_id)
VALUES ('Monday', current_date, 100003),
       ('Monday', current_date, 100004);

INSERT INTO dishes (description, price, menu_id)
VALUES ('Pizza', 150, 100005),
       ('Tea', 50, 100005),
       ('Burger', 200, 100006);

-- INSERT INTO VOTES (date, time, restaurant_id, user_id)
-- VALUES ('2020-04-01', '12:00:00', 100002, 100000),
--        ('2020-04-01', '11:00:00', 100002, 100001),
--        ('2020-04-28', '12:00:00', 100002, 100000),
--        ('2020-04-28', '11:00:00', 100002, 100001);



