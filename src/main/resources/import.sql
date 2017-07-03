-- add restaurants
insert into "restaurant" (name, city, street, latitude, longitude, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count, is_active) values ('Piano Bar Restauant & Cafe', 'poznań', 'Półwiejska 42', 52.407640, 16.932010,'Piano Bar jest urokliwym, stylowym miejscem, które znajduje się w Centrum Sztuki i Biznesu w Starym Browarze. Jest niepowtarzalne i przepełnione niezwykłym klimatem. Piano Bar to restauracja, którą wyróżnia profesjonalna obsługa sprawiająca, że nasi Goście czują się naprawdę niezwykle. Dodatkowy, elegancki a zarazem ciepły wystrój wnętrza zapewnia uczucie wyjątkowości. Nasze smaki to kuchnia włoska, śródziemnomorska i potrawy kuchni polskiej. Szef Kuchni Krystian Szopka z zespołem kucharzy sprawia, że jej smak na długo pozostaje w pamięci naszych Gości. Zapraszamy na lunche, rodzinne obiady, spotkania biznesowe.', 4.5, 5.0, 5.0, 5.0, 20, 1800, 72, true);
insert into "restaurant" (name, city, street, latitude, longitude, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count, is_active) values ('Restauracja Ratuszova', 'poznań', 'Stary Rynek 55', 52.409640, 16.32817,'Restauracja Ratuszova serdecznie zaprasza w swoje progi wszystkich, którzy lubią lub pragną zasmakować tradycyjne potrawy kuchni polskiej! W naszym menu na szczególną uwagę zasługuje czernina z domowym makaronem, kaczka pieczona z jabłkami oraz dania z dziczyzny. Miłośników Slow Food, pasjonatów zdrowego odżywiania oraz wszystkich smakoszy w szczególności zachęcamy do spróbowania dań gotowanych innowacyjną metodą sous-vide. Dla jeszcze większego urozmaicenia nasze menu uzupełniliśmy smakami kuchni międzynarodowej. W naszej restauracji dbamy o to by sezonowo zmieniać potrawy oraz wystrój sal. ', 4.5, 5.0, 5.0, 5.0, 25, 1800, 34, true);
insert into "restaurant" (name, city, street, latitude, longitude, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count, is_active) values ('Manekin', 'poznań', 'Kwiatowa 3', 52.407640, 16.932010,'Znana i ceniona sieć lokali podająca przepyszne naleśniki. Restauracja serwuje wiele rodzajów naleśników na słodko jak i na wytrawnie ale nie tylko. Tu można zjeść również sałatki, zupy, desery, spaghetti. Ogromnym atutem jest stylizowane wnętrze, nawiązujące do krainy baśniowej. W tym surrealistycznym otoczeniu można nie tylko zjeść, lecz również zrelaksować się przy kieliszku wina czy kuflu piwa.', 4.7, 4.0, 3.0, 3.5, 30, 1800, 12, true);
insert into "restaurant" (name, city, street, latitude, longitude, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count, is_active) values ('Whiskey in the Jar', 'poznań', 'Stary Rynek 100', 52.312312, 16.932010,'Whiskey in the Jar to steakhouse, wzorowany na najlepszych amerykańskich lokalach tego typu. W menu znajdują się głównie dania mięsne – specjalnością są steki, przyrządzane z najwyższej jakości wołowiny, a także burgery i dania grillowane, dostępne pod nazwą Grill Rockersa. Wszystkie potrawy przygotowywane są na najdłuższym w Poznaniu grillu lawowym. Ogromnym powodzeniem cieszą się również autorskie drinki na bazie Jacka Daniels’a, zgodnie z nazwą lokalu podawane w designerskich słojach – jarach.', 4.5, 5.0, 5.0, 5.0, 25, 1800, 34, true);
insert into "restaurant" (name, city, street, latitude, longitude, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count, is_active) values ('Chmielnik Pub', 'poznań', 'Żydowska 27', 52.938471, 16.203918,'Ambasador polskiego piwa w Poznaniu. Największy wybór piw z polskich browarów rzemieślniczych w samym sercu Poznania.  Zapewniamy wyjątkowy wystrój, profesjonalną obsługę, niepowtarzalny klimat. W sezonie letnim zapraszamy do magicznego ogrodu piwnego dla 200 osób, w którym główną atrakcją jest rosnący chmiel w odmianie SYBILLA oraz działająca kuchnia oferująca dania dopasowane do piwa.',4.5, 5.0, 5.0, 5.0, 20, 1800,72, true);
insert into "restaurant" (name, city, street, latitude, longitude, description, rate, service_rate, place_rate, price_quality_rate, capacity, avg_reservation_time, opinion_count, is_active) values ('Shipudei Berek', 'warszawa', 'Jasna 24', 53.391746, 16.293816,'Berek to rodzaj pocztówki z wakacji. Radość śródziemnomorskiego jedzenia i gorący klimat telawiwskich ulic, którego wspomnienie przeniesione zostało do Warszawy. Tworząc menu w Berku starałem się poznać lokalnych producentów i wykorzystywać jak najświeższe składniki. Wychowałem się w końcu w miejscu, gdzie owoce zjada się prosto z drzewa. Nasza pita jest wypiekana na miejscu i podawana na gorąco, hummus robimy taki, jaki sami jemy, korzystamy przede wszystkim z sezonowych warzyw. W Berku dzielę się moimi ulubionymi smakami domu, we wdzięcznym, współczesnym wydaniu.',4.8, 4.0, 4.4, 4.7, 30, 1800,13, true);


-- add users
insert into "user" (email, name, surname, restaurant_id, account_state) values ('przemyslaw.pawlicki@gmail.com', 'Przemysław', 'Pawlicki', 1, 0);
insert into "user" (email, name, surname, restaurant_id, account_state) values ('bartosz.zmarzlik@gmail.com', 'Bartosz', 'Zmarzlik', 2, 0);
insert into "user" (email, name, surname, restaurant_id, account_state) values ('janusz.kolodziej@gmail.com', 'Janusz', 'Kołodziej', 3, 0);
insert into "user" (email, name, surname, restaurant_id, account_state) values ('bartosz.smektala@gmail.com', 'Bartosz', 'Smektała', 4, 0);
insert into "user" (email, name, surname, restaurant_id, account_state) values ('martyna.wierzbicka@gmail.com', 'Martyna', 'Wierzbicka', 5, 0);
insert into "user" (email, name, surname, restaurant_id, account_state) values ('ofir.vidavsky@gmail.com', 'Ofir', 'Vidavsky', 6, 0);


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
insert into "restaurant_kitchen" (restaurant_id, kitchen_type) values (6, 'VIET_THAI');

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

insert into "business_hour" (day_of_week, open, close) values (0, '10:00:00', '21:00:00');
insert into "business_hour" (day_of_week, open, close) values (1, '10:00:00', '21:00:00');
insert into "business_hour" (day_of_week, open, close) values (2, '10:00:00', '21:00:00');
insert into "business_hour" (day_of_week, open, close) values (3, '10:00:00', '21:00:00');
insert into "business_hour" (day_of_week, open, close) values (4, '10:00:00', '23:00:00');
insert into "business_hour" (day_of_week, open, close) values (5, '10:00:00', '23:00:00');
insert into "business_hour" (day_of_week, open, close) values (6, '10:00:00', '23:00:00');
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (6,36);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (6,37);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (6,38);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (6,39);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (6,40);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (6,41);
insert into "restaurant_business_hour" (restaurant_id, business_hour_id) values (6,42);

-- add customers
insert into "customer" (email, phone_number, name, surname) values ('karola.szafranska@gmail.pl', '666894323', 'Karola', 'Szafrańska');
insert into "customer" (email, phone_number, name, surname) values ('maciej.wlodarczyk@gmail.pl', '883991105', 'Maciej', 'Włodarczyk');
insert into "customer" (email, phone_number, name, surname) values ('sergiusz.malecki@gmail.pl', '739354158', 'Sergiusz', 'Małecki');
insert into "customer" (email, phone_number, name, surname) values ('jagoda.fabisiak@gmail.pl', '789000731', 'Jagoda', 'Fabisiak');
insert into "customer" (email, phone_number, name, surname) values ('maurycy.lisowski@gmail.pl', '729712835', 'Maurycy', 'Lisowski');

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
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (5, '2017-07-18', '2017-07-18 14:50:00.000', 1800, '2017-07-18 15:20:00.000', 0, 1, 1, 314251, true);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (15, '2017-07-18','2017-07-18 14:50:00.000', 1800, '2017-07-18 15:20:00.000', 0, 2, 2, 142512, true);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (7, '2017-07-18','2017-07-18 15:00:00.000', 1800, '2017-07-18 15:30:00.000', 0, 2, 1, 572456, true);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (3, '2017-07-18','2017-07-18 15:10:00.000', 1800, '2017-07-18 15:40:00.000', 0, 3, 1, 123683, true);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (5, '2017-07-18','2017-07-18 15:30:00.000', 1800, '2017-07-18 16:00:00.000', 0, 4, 1, 946784, true);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (20, '2017-07-18','2017-07-18 13:30:00.000', 1800, '2017-07-18 14:00:00.000', 0, 5, 1, 136578, false);
INSERT INTO "reservation" (people_number, date, reservation_begin, length, reservation_end, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (5, '2017-07-19', '2017-07-19 14:50:00.000', 1800, '2017-07-19 15:20:00.000', 0, 1, 1, 846789, true);
