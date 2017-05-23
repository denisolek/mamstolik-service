-- add restaurants
INSERT INTO "restaurant" (name, city, street, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count)
VALUES ('Piano Bar Restauant & Cafe', 'poznań', 'Półwiejska 42',
                                      'Piano Bar jest urokliwym, stylowym miejscem, które znajduje się w Centrum Sztuki i Biznesu w Starym Browarze. Jest niepowtarzalne i przepełnione niezwykłym klimatem. Piano Bar to restauracja, którą wyróżnia profesjonalna obsługa sprawiająca, że nasi Goście czują się naprawdę niezwykle. Dodatkowy, elegancki a zarazem ciepły wystrój wnętrza zapewnia uczucie wyjątkowości. Nasze smaki to kuchnia włoska, śródziemnomorska i potrawy kuchni polskiej. Szef Kuchni Krystian Szopka z zespołem kucharzy sprawia, że jej smak na długo pozostaje w pamięci naszych Gości. Zapraszamy na lunche, rodzinne obiady, spotkania biznesowe.',
                                      4.5, 5.0, 5.0, 5.0, 20, 1800, 72);
INSERT INTO "restaurant" (name, city, street, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count)
VALUES ('Restauracja Ratuszova', 'poznań', 'Stary Rynek 55',
                                 'Restauracja Ratuszova serdecznie zaprasza w swoje progi wszystkich, którzy lubią lub pragną zasmakować tradycyjne potrawy kuchni polskiej! W naszym menu na szczególną uwagę zasługuje czernina z domowym makaronem, kaczka pieczona z jabłkami oraz dania z dziczyzny. Miłośników Slow Food, pasjonatów zdrowego odżywiania oraz wszystkich smakoszy w szczególności zachęcamy do spróbowania dań gotowanych innowacyjną metodą sous-vide. Dla jeszcze większego urozmaicenia nasze menu uzupełniliśmy smakami kuchni międzynarodowej. W naszej restauracji dbamy o to by sezonowo zmieniać potrawy oraz wystrój sal. ',
                                 4.5, 5.0, 5.0, 5.0, 25, 1800, 34);
INSERT INTO "restaurant" (name, city, street, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count)
VALUES ('Manekin', 'poznań', 'Kwiatowa 3',
                   'Znana i ceniona sieć lokali podająca przepyszne naleśniki. Restauracja serwuje wiele rodzajów naleśników na słodko jak i na wytrawnie ale nie tylko. Tu można zjeść również sałatki, zupy, desery, spaghetti. Ogromnym atutem jest stylizowane wnętrze, nawiązujące do krainy baśniowej. W tym surrealistycznym otoczeniu można nie tylko zjeść, lecz również zrelaksować się przy kieliszku wina czy kuflu piwa.',
                   4.7, 4.0, 3.0, 3.5, 30, 1800, 12);
INSERT INTO "restaurant" (name, city, street, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count)
VALUES ('Whiskey in the Jar', 'poznań', 'Stary Rynek 100',
                              'Whiskey in the Jar to steakhouse, wzorowany na najlepszych amerykańskich lokalach tego typu. W menu znajdują się głównie dania mięsne – specjalnością są steki, przyrządzane z najwyższej jakości wołowiny, a także burgery i dania grillowane, dostępne pod nazwą Grill Rockersa. Wszystkie potrawy przygotowywane są na najdłuższym w Poznaniu grillu lawowym. Ogromnym powodzeniem cieszą się również autorskie drinki na bazie Jacka Daniels’a, zgodnie z nazwą lokalu podawane w designerskich słojach – jarach.',
                              4.5, 5.0, 5.0, 5.0, 25, 1800, 34);
INSERT INTO "restaurant" (name, city, street, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count)
VALUES ('Chmielnik Pub', 'poznań', 'Żydowska 27',
                         'Ambasador polskiego piwa w Poznaniu. Największy wybór piw z polskich browarów rzemieślniczych w samym sercu Poznania.  Zapewniamy wyjątkowy wystrój, profesjonalną obsługę, niepowtarzalny klimat. W sezonie letnim zapraszamy do magicznego ogrodu piwnego dla 200 osób, w którym główną atrakcją jest rosnący chmiel w odmianie SYBILLA oraz działająca kuchnia oferująca dania dopasowane do piwa.',
                         4.5, 5.0, 5.0, 5.0, 20, 1800, 72);

-- add users
INSERT INTO "user" (email, name, surname, restaurant_id)
VALUES ('przemyslaw.pawlicki@gmail.com', 'Przemysław', 'Pawlicki', 1);
INSERT INTO "user" (email, name, surname, restaurant_id)
VALUES ('bartosz.zmarzlik@gmail.com', 'Bartosz', 'Zmarzlik', 2);
INSERT INTO "user" (email, name, surname, restaurant_id)
VALUES ('janusz.kolodziej@gmail.com', 'Janusz', 'Kołodziej', 3);
INSERT INTO "user" (email, name, surname, restaurant_id)
VALUES ('bartosz.smektala@gmail.com', 'Bartosz', 'Smektała', 4);
INSERT INTO "user" (email, name, surname, restaurant_id)
VALUES ('martyna.wierzbicka@gmail.com', 'Martyna', 'Wierzbicka', 5);

-- add kitchen types
INSERT INTO "restaurant_kitchen" (restaurant_id, kitchen_type) VALUES (1, 'POLISH');
INSERT INTO "restaurant_kitchen" (restaurant_id, kitchen_type) VALUES (1, 'GREEK');
INSERT INTO "restaurant_kitchen" (restaurant_id, kitchen_type) VALUES (2, 'ITALIAN');
INSERT INTO "restaurant_kitchen" (restaurant_id, kitchen_type) VALUES (2, 'INDIAN');
INSERT INTO "restaurant_kitchen" (restaurant_id, kitchen_type) VALUES (2, 'VIET_THAI');
INSERT INTO "restaurant_kitchen" (restaurant_id, kitchen_type) VALUES (3, 'ASIAN');
INSERT INTO "restaurant_kitchen" (restaurant_id, kitchen_type) VALUES (3, 'JAPANESE');
INSERT INTO "restaurant_kitchen" (restaurant_id, kitchen_type) VALUES (3, 'VIET_THAI');
INSERT INTO "restaurant_kitchen" (restaurant_id, kitchen_type) VALUES (4, 'BURGERS');
INSERT INTO "restaurant_kitchen" (restaurant_id, kitchen_type) VALUES (4, 'MEXICAN');
INSERT INTO "restaurant_kitchen" (restaurant_id, kitchen_type) VALUES (5, 'POLISH');

-- add business hours
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (0, '12:00:00', '21:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (1, '12:00:00', '21:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (2, '12:00:00', '21:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (3, '12:00:00', '21:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (4, '13:00:00', '23:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (5, '13:00:00', '23:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (6, '13:00:00', '20:00:00');
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (1, 1);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (1, 2);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (1, 3);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (1, 4);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (1, 5);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (1, 6);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (1, 7);

INSERT INTO "business_hour" (day_of_week, open, close) VALUES (0, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (1, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (2, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (3, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (4, '10:00:00', '22:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (5, '13:00:00', '22:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (6, '13:00:00', '22:00:00');
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (2, 8);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (2, 9);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (2, 10);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (2, 11);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (2, 12);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (2, 13);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (2, 14);

INSERT INTO "business_hour" (day_of_week, open, close) VALUES (0, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (1, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (2, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (3, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (4, '10:00:00', '22:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (5, '13:00:00', '22:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (6, '13:00:00', '22:00:00');
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (3, 15);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (3, 16);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (3, 17);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (3, 18);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (3, 19);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (3, 20);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (3, 21);

INSERT INTO "business_hour" (day_of_week, open, close) VALUES (0, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (1, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (2, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (3, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (4, '10:00:00', '22:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (5, '13:00:00', '22:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (6, '13:00:00', '22:00:00');
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (4, 22);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (4, 23);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (4, 24);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (4, 25);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (4, 26);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (4, 27);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (4, 28);

INSERT INTO "business_hour" (day_of_week, open, close) VALUES (0, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (1, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (2, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (3, '10:00:00', '19:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (4, '10:00:00', '22:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (5, '13:00:00', '22:00:00');
INSERT INTO "business_hour" (day_of_week, open, close) VALUES (6, '13:00:00', '22:00:00');
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (5, 29);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (5, 30);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (5, 31);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (5, 32);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (5, 33);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (5, 34);
INSERT INTO "restaurant_business_hour" (restaurant_id, business_hour_id) VALUES (5, 35);

-- add customers
INSERT INTO "customer" (email, phone_number, name, surname)
VALUES ('test1@test.pl', '111 111 111', 'TestName 1', 'TestSurname 1');
INSERT INTO "customer" (email, phone_number, name, surname)
VALUES ('test2@test.pl', '222 222 222', 'TestName 2', 'TestSurname 2');
INSERT INTO "customer" (email, phone_number, name, surname)
VALUES ('test3@test.pl', '333 333 333', 'TestName 3', 'TestSurname 3');
INSERT INTO "customer" (email, phone_number, name, surname)
VALUES ('test4@test.pl', '444 444 444', 'TestName 4', 'TestSurname 4');
INSERT INTO "customer" (email, phone_number, name, surname)
VALUES ('test5@test.pl', '555 555 555', 'TestName 5', 'TestSurname 5');

-- add spots
INSERT INTO "spot" (capacity, restaurant_id) VALUES (4, 1);
INSERT INTO "spot" (capacity, restaurant_id) VALUES (4, 1);
INSERT INTO "spot" (capacity, restaurant_id) VALUES (5, 1);
INSERT INTO "spot" (capacity, restaurant_id) VALUES (5, 1);
INSERT INTO "spot" (capacity, restaurant_id) VALUES (2, 1);
INSERT INTO "spot" (capacity, restaurant_id) VALUES (6, 2);
INSERT INTO "spot" (capacity, restaurant_id) VALUES (7, 2);
INSERT INTO "spot" (capacity, restaurant_id) VALUES (8, 2);
INSERT INTO "spot" (capacity, restaurant_id) VALUES (3, 2);
INSERT INTO "spot" (capacity, restaurant_id) VALUES (1, 2);

-- add reservations
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id)
VALUES (5, '2017-05-18', '2017-05-18 14:50:00.000', 1800, '2017-05-18 15:20:00.000', 1, 1, 1);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id)
VALUES (15, '2017-05-18', '2017-05-18 14:50:00.000', 1800, '2017-05-18 15:20:00.000', 1, 2, 2);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id)
VALUES (7, '2017-05-18', '2017-05-18 15:00:00.000', 1800, '2017-05-18 15:30:00.000', 1, 2, 1);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id)
VALUES (3, '2017-05-18', '2017-05-18 15:10:00.000', 1800, '2017-05-18 15:40:00.000', 1, 3, 1);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id)
VALUES (5, '2017-05-18', '2017-05-18 15:30:00.000', 1800, '2017-05-18 16:00:00.000', 1, 4, 1);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id)
VALUES (20, '2017-05-18', '2017-05-18 13:30:00.000', 1800, '2017-05-18 14:00:00.000', 1, 5, 1);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id)
VALUES (5, '2017-05-19', '2017-05-19 14:50:00.000', 1800, '2017-05-19 15:20:00.000', 1, 1, 1);
