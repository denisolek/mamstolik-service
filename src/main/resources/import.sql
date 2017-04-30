-- add users
insert into "user" (email, name, surname) values ('john.diggle@gmail.com', 'John', 'Diggle');
insert into "user" (email, name, surname) values ('emily.wong@gmail.com', 'Emily', 'Wong');
insert into "user" (email, name, surname) values ('judah.sanchez@gmail.com', 'Judah', 'Sanchez');

-- add restaurants
insert into "restaurant" (name, city) values ('Pastwisko', 'Poznań');
insert into "restaurant" (name, city) values ('Michael Angelo & Gotham Pizza', 'Poznań');

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
