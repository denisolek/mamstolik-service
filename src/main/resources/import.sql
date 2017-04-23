insert into "user" (email, name, surname) values ('john.diggle@gmail.com', 'John', 'Diggle');
insert into "user" (email, name, surname) values ('emily.wong@gmail.com', 'Emily', 'Wong');
insert into "user" (email, name, surname) values ('judah.sanchez@gmail.com', 'Judah', 'Sanchez');

insert into "restaurant" (name, city) values ('Pastwisko', 'Poznań');
insert into "restaurant" (name, city) values ('Michael Angelo & Gotham Pizza', 'Poznań');

insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (1, 'BURGERS');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (2, 'ITALIAN');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (2, 'ARABIC_TURKISH');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (2, 'BURGERS');