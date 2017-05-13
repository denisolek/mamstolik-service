-- add restaurants
insert into "restaurant" (name, city, capacity, avg_reservation_time) values ('Pastwisko', 'Poznań', 20, 1800);
insert into "restaurant" (name, city, capacity, avg_reservation_time) values ('Michael Angelo & Gotham Pizza', 'Poznań', 25, 1800);

-- add users
insert into "user" (email, name, surname, restaurant_id) values ('john.diggle@gmail.com', 'John', 'Diggle', 1);
insert into "user" (email, name, surname, restaurant_id) values ('emily.wong@gmail.com', 'Emily', 'Wong', 2);

-- add kitchen types
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (1, 'BURGERS');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (2, 'ITALIAN');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (2, 'ARABIC_TURKISH');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (2, 'BURGERS');

-- add business hours
insert into "business_hour" (day_of_week, open, close) values (0, '12:00:00', '21:00:00');
insert into "business_hour" (day_of_week, open, close) values (1, '12:00:00', '21:00:00');
insert into "business_hour" (day_of_week, open, close) values (2, '12:00:00', '21:00:00');
insert into "business_hour" (day_of_week, open, close) values (3, '12:00:00', '21:00:00');
insert into "business_hour" (day_of_week, open, close) values (4, '13:00:00', '23:00:00');
insert into "business_hour" (day_of_week, open, close) values (5, '13:00:00', '23:00:00');
insert into "business_hour" (day_of_week, open, close) values (6, '13:00:00', '20:00:00');

insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (1,1);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (1,2);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (1,3);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (1,4);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (1,5);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (1,6);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (1,7);

insert into "business_hour" (day_of_week, open, close) values (0, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (1, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (2, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (3, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (4, '10:00:00', '22:00:00');
insert into "business_hour" (day_of_week, open, close) values (5, '13:00:00', '22:00:00');
insert into "business_hour" (day_of_week, open, close) values (6, '13:00:00', '22:00:00');

insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (2,8);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (2,9);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (2,10);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (2,11);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (2,12);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (2,13);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (2,14);

-- add customers
insert into "customer" (email, phone_number, name, surname) values ('test1@test.pl', '111 111 111', 'TestName 1', 'TestSurname 1');
insert into "customer" (email, phone_number, name, surname) values ('test2@test.pl', '222 222 222', 'TestName 2', 'TestSurname 2');
insert into "customer" (email, phone_number, name, surname) values ('test3@test.pl', '333 333 333', 'TestName 3', 'TestSurname 3');
insert into "customer" (email, phone_number, name, surname) values ('test4@test.pl', '444 444 444', 'TestName 4', 'TestSurname 4');
insert into "customer" (email, phone_number, name, surname) values ('test5@test.pl', '555 555 555', 'TestName 5', 'TestSurname 5');

-- add spots
insert into "spot" (capacity, restaurant_id) values (4, 1);
insert into "spot" (capacity, restaurant_id) values (4, 1);
insert into "spot" (capacity, restaurant_id) values (5, 1);
insert into "spot" (capacity, restaurant_id) values (5, 1);
insert into "spot" (capacity, restaurant_id) values (2, 1);
insert into "spot" (capacity, restaurant_id) values (6, 2);
insert into "spot" (capacity, restaurant_id) values (7, 2);
insert into "spot" (capacity, restaurant_id) values (8, 2);
insert into "spot" (capacity, restaurant_id) values (3, 2);
insert into "spot" (capacity, restaurant_id) values (1, 2);

-- add reservations
INSERT INTO "reservation" (people_number, reservation_begin, length, reservation_end, state, customer_id, restaurant_id) VALUES (5, '2017-05-20 09:50:00.000', 1800, '2017-05-20 10:20:00.000', 1, 1, 1);
INSERT INTO "reservation" (people_number, reservation_begin, length, reservation_end, state, customer_id, restaurant_id) VALUES (15, '2017-05-20 09:50:00.000', 1800, '2017-05-20 10:20:00.000', 1, 2, 2);
INSERT INTO "reservation" (people_number, reservation_begin, length, reservation_end, state, customer_id, restaurant_id) VALUES (7, '2017-05-20 10:00:00.000', 1800, '2017-05-20 10:30:00.000', 1, 2, 1);
INSERT INTO "reservation" (people_number, reservation_begin, length, reservation_end, state, customer_id, restaurant_id) VALUES (3, '2017-05-20 10:10:00.000', 1800, '2017-05-20 10:40:00.000', 1, 3, 1);
INSERT INTO "reservation" (people_number, reservation_begin, length, reservation_end, state, customer_id, restaurant_id) VALUES (5, '2017-05-20 10:30:00.000', 1800, '2017-05-20 11:00:00.000', 1, 4, 1);
INSERT INTO "reservation" (people_number, reservation_begin, length, reservation_end, state, customer_id, restaurant_id) VALUES (20, '2017-05-20 12:30:00.000', 1800, '2017-05-20 12:30:00.000', 1, 5, 1);
