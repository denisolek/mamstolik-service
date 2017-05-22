-- add restaurants
insert into "restaurant" (name, city, street, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count) values ('Piano Bar Restauant & Cafe', 'poznań', 'Półwiejska 42', 'Piano Bar jest urokliwym, stylowym miejscem, które znajduje się w Centrum Sztuki i Biznesu w Starym Browarze. Jest niepowtarzalne i przepełnione niezwykłym klimatem. Piano Bar to restauracja, którą wyróżnia profesjonalna obsługa sprawiająca, że nasi Goście czują się naprawdę niezwykle. Dodatkowy, elegancki a zarazem ciepły wystrój wnętrza zapewnia uczucie wyjątkowości. Nasze smaki to kuchnia włoska, śródziemnomorska i potrawy kuchni polskiej. Szef Kuchni Krystian Szopka z zespołem kucharzy sprawia, że jej smak na długo pozostaje w pamięci naszych Gości. Zapraszamy na lunche, rodzinne obiady, spotkania biznesowe.', 4.5, 5.0, 5.0, 5.0, 20, 1800, 72);
insert into "restaurant" (name, city, street, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count) values ('Restauracja Ratuszova', 'poznań', 'Stary Rynek 55', 'Restauracja Ratuszova serdecznie zaprasza w swoje progi wszystkich, którzy lubią lub pragną zasmakować tradycyjne potrawy kuchni polskiej! W naszym menu na szczególną uwagę zasługuje czernina z domowym makaronem, kaczka pieczona z jabłkami oraz dania z dziczyzny. Miłośników Slow Food, pasjonatów zdrowego odżywiania oraz wszystkich smakoszy w szczególności zachęcamy do spróbowania dań gotowanych innowacyjną metodą sous-vide. Dla jeszcze większego urozmaicenia nasze menu uzupełniliśmy smakami kuchni międzynarodowej. W naszej restauracji dbamy o to by sezonowo zmieniać potrawy oraz wystrój sal. ', 4.5, 5.0, 5.0, 5.0, 25, 1800, 34);
insert into "restaurant" (name, city, street, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count) values ('Manekin', 'poznań', 'Kwiatowa 3', 'Znana i ceniona sieć lokali podająca przepyszne naleśniki. Restauracja serwuje wiele rodzajów naleśników na słodko jak i na wytrawnie ale nie tylko. Tu można zjeść również sałatki, zupy, desery, spaghetti. Ogromnym atutem jest stylizowane wnętrze, nawiązujące do krainy baśniowej. W tym surrealistycznym otoczeniu można nie tylko zjeść, lecz również zrelaksować się przy kieliszku wina czy kuflu piwa.', 4.7, 4.0, 3.0, 3.5, 30, 1800, 12);
insert into "restaurant" (name, city, street, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count) values ('Whiskey in the Jar', 'poznań', 'Stary Rynek 100', 'Whiskey in the Jar to steakhouse, wzorowany na najlepszych amerykańskich lokalach tego typu. W menu znajdują się głównie dania mięsne – specjalnością są steki, przyrządzane z najwyższej jakości wołowiny, a także burgery i dania grillowane, dostępne pod nazwą Grill Rockersa. Wszystkie potrawy przygotowywane są na najdłuższym w Poznaniu grillu lawowym. Ogromnym powodzeniem cieszą się również autorskie drinki na bazie Jacka Daniels’a, zgodnie z nazwą lokalu podawane w designerskich słojach – jarach.', 4.5, 5.0, 5.0, 5.0, 25, 1800, 34);
insert into "restaurant" (name, city, street, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count) values ('Chmielnik Pub', 'poznań', 'Żydowska 27', 'Ambasador polskiego piwa w Poznaniu. Największy wybór piw z polskich browarów rzemieślniczych w samym sercu Poznania.  Zapewniamy wyjątkowy wystrój, profesjonalną obsługę, niepowtarzalny klimat. W sezonie letnim zapraszamy do magicznego ogrodu piwnego dla 200 osób, w którym główną atrakcją jest rosnący chmiel w odmianie SYBILLA oraz działająca kuchnia oferująca dania dopasowane do piwa.',4.5, 5.0, 5.0, 5.0, 20, 1800,72);

-- add users
insert into "user" (email, name, surname, restaurant_id) values ('przemyslaw.pawlicki@gmail.com', 'Przemysław', 'Pawlicki', 1);
insert into "user" (email, name, surname, restaurant_id) values ('bartosz.zmarzlik@gmail.com', 'Bartosz', 'Zmarzlik', 2);
insert into "user" (email, name, surname, restaurant_id) values ('janusz.kolodziej@gmail.com', 'Janusz', 'Kołodziej', 3);
insert into "user" (email, name, surname, restaurant_id) values ('bartosz.smektala@gmail.com', 'Bartosz', 'Smektała', 4);
insert into "user" (email, name, surname, restaurant_id) values ('martyna.wierzbicka@gmail.com', 'Martyna', 'Wierzbicka', 5);

-- add kitchen types
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (1, 'POLISH');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (1, 'GREEK');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (2, 'ITALIAN');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (2, 'INDIAN');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (2, 'VIET_THAI');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (3, 'ASIAN');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (3, 'JAPANESE');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (3, 'VIET_THAI');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (4, 'BURGERS');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (4, 'MEXICAN');
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (5, 'POLISH');



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

insert into "business_hour" (day_of_week, open, close) values (0, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (1, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (2, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (3, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (4, '10:00:00', '22:00:00');
insert into "business_hour" (day_of_week, open, close) values (5, '13:00:00', '22:00:00');
insert into "business_hour" (day_of_week, open, close) values (6, '13:00:00', '22:00:00');
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (3,15);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (3,16);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (3,17);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (3,18);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (3,19);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (3,20);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (3,21);

insert into "business_hour" (day_of_week, open, close) values (0, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (1, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (2, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (3, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (4, '10:00:00', '22:00:00');
insert into "business_hour" (day_of_week, open, close) values (5, '13:00:00', '22:00:00');
insert into "business_hour" (day_of_week, open, close) values (6, '13:00:00', '22:00:00');
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (4,22);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (4,23);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (4,24);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (4,25);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (4,26);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (4,27);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (4,28);

insert into "business_hour" (day_of_week, open, close) values (0, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (1, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (2, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (3, '10:00:00', '19:00:00');
insert into "business_hour" (day_of_week, open, close) values (4, '10:00:00', '22:00:00');
insert into "business_hour" (day_of_week, open, close) values (5, '13:00:00', '22:00:00');
insert into "business_hour" (day_of_week, open, close) values (6, '13:00:00', '22:00:00');
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (5,29);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (5,30);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (5,31);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (5,32);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (5,33);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (5,34);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (5,35);


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
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id) VALUES (5, '2017-05-18', '2017-05-18 14:50:00.000', 1800, '2017-05-18 15:20:00.000', 1, 1, 1);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id) VALUES (15, '2017-05-18','2017-05-18 14:50:00.000', 1800, '2017-05-18 15:20:00.000', 1, 2, 2);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id) VALUES (7, '2017-05-18','2017-05-18 15:00:00.000', 1800, '2017-05-18 15:30:00.000', 1, 2, 1);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id) VALUES (3, '2017-05-18','2017-05-18 15:10:00.000', 1800, '2017-05-18 15:40:00.000', 1, 3, 1);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id) VALUES (5, '2017-05-18','2017-05-18 15:30:00.000', 1800, '2017-05-18 16:00:00.000', 1, 4, 1);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id) VALUES (20, '2017-05-18','2017-05-18 13:30:00.000', 1800, '2017-05-18 14:00:00.000', 1, 5, 1);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id) VALUES (5, '2017-05-19', '2017-05-19 14:50:00.000', 1800, '2017-05-19 15:20:00.000', 1, 1, 1);
