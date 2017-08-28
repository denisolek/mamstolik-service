/* @formatter:off */
ALTER TABLE public.restaurant ALTER COLUMN "description" TYPE VARCHAR(3000);

INSERT INTO public.authority (name) VALUES ('ROLE_OWNER');
INSERT INTO public.authority (name) VALUES ('ROLE_ADMIN');

INSERT INTO public.city (name) VALUES ('Poznań');
INSERT INTO public.city (name) VALUES ('Warszawa');
INSERT INTO public.city (name) VALUES ('Gorzów Wielkopolski');

-- city
INSERT INTO public.city_alias (name, city_id) VALUES ('Pzn', 1);
INSERT INTO public.city_alias (name, city_id) VALUES ('Gorzów Wlkp', 3);
INSERT INTO public.city_alias (name, city_id) VALUES ('Gorzów Wlkp.', 3);
INSERT INTO public.city_alias (name, city_id) VALUES ('GW', 3);

-- city
INSERT INTO public.address (building_number, latitude, longitude, postal_code, street_name, city_id) VALUES ('1A', 52.407640, 16.932010, '60-681', 'Półwiejska 42', 1);
INSERT INTO public.address (building_number, latitude, longitude, postal_code, street_name, city_id) VALUES ('2B', 52.409640, 16.32817, '60-681', 'Stary Rynek 55', 1);
INSERT INTO public.address (building_number, latitude, longitude, postal_code, street_name, city_id) VALUES ('3C', 52.407640, 16.932010, '60-681', 'Kwiatowa 3', 1);
INSERT INTO public.address (building_number, latitude, longitude, postal_code, street_name, city_id) VALUES ('4D', 52.312312, 16.932010, '60-681', 'Stary Rynek 100', 1);
INSERT INTO public.address (building_number, latitude, longitude, postal_code, street_name, city_id) VALUES ('5E', 52.407640, 16.932010, '60-681', 'Żydowska 27', 2);
INSERT INTO public.address (building_number, latitude, longitude, postal_code, street_name, city_id) VALUES ('6F', 52.407640, 16.932010, '60-681', 'Jasna 24', 2);
INSERT INTO public.address (building_number, latitude, longitude, postal_code, street_name, city_id) VALUES ('7G', 52.407640, 16.932010, '60-681', 'Batorego 82', 3);


-- address
INSERT INTO public.restaurant (name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, description) VALUES ('Piano Bar Restauant & Cafe', 1, 4.5, 5.0, 5.0, 5.0, 1800, true, 'Piano Bar jest urokliwym, stylowym miejscem, które znajduje się w Centrum Sztuki i Biznesu w Starym Browarze. Jest niepowtarzalne i przepełnione niezwykłym klimatem. Piano Bar to restauracja, którą wyróżnia profesjonalna obsługa sprawiająca, że nasi Goście czują się naprawdę niezwykle. Dodatkowy, elegancki a zarazem ciepły wystrój wnętrza zapewnia uczucie wyjątkowości. Nasze smaki to kuchnia włoska, śródziemnomorska i potrawy kuchni polskiej. Szef Kuchni Krystian Szopka z zespołem kucharzy sprawia, że jej smak na długo pozostaje w pamięci naszych Gości. Zapraszamy na lunche, rodzinne obiady, spotkania biznesowe.');
INSERT INTO public.restaurant (name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, description) VALUES ('Restauracja Ratuszova', 2, 4.5, 5.0, 5.0, 5.0, 1800, true, 'Restauracja Ratuszova serdecznie zaprasza w swoje progi wszystkich, którzy lubią lub pragną zasmakować tradycyjne potrawy kuchni polskiej! W naszym menu na szczególną uwagę zasługuje czernina z domowym makaronem, kaczka pieczona z jabłkami oraz dania z dziczyzny. Miłośników Slow Food, pasjonatów zdrowego odżywiania oraz wszystkich smakoszy w szczególności zachęcamy do spróbowania dań gotowanych innowacyjną metodą sous-vide. Dla jeszcze większego urozmaicenia nasze menu uzupełniliśmy smakami kuchni międzynarodowej. W naszej restauracji dbamy o to by sezonowo zmieniać potrawy oraz wystrój sal. ');
INSERT INTO public.restaurant (name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, description) VALUES ('Manekin', 3, 4.7, 4.0, 3.0, 3.5, 1800, true, 'Znana i ceniona sieć lokali podająca przepyszne naleśniki. Restauracja serwuje wiele rodzajów naleśników na słodko jak i na wytrawnie ale nie tylko. Tu można zjeść również sałatki, zupy, desery, spaghetti. Ogromnym atutem jest stylizowane wnętrze, nawiązujące do krainy baśniowej. W tym surrealistycznym otoczeniu można nie tylko zjeść, lecz również zrelaksować się przy kieliszku wina czy kuflu piwa.');
INSERT INTO public.restaurant (name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, description) VALUES ('Whiskey in the Jar', 4, 4.5, 5.0, 5.0, 5.0, 1800, true, 'Whiskey in the Jar to steakhouse, wzorowany na najlepszych amerykańskich lokalach tego typu. W menu znajdują się głównie dania mięsne – specjalnością są steki, przyrządzane z najwyższej jakości wołowiny, a także burgery i dania grillowane, dostępne pod nazwą Grill Rockersa. Wszystkie potrawy przygotowywane są na najdłuższym w Poznaniu grillu lawowym. Ogromnym powodzeniem cieszą się również autorskie drinki na bazie Jacka Daniels’a, zgodnie z nazwą lokalu podawane w designerskich słojach – jarach.');
INSERT INTO public.restaurant (name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, description) VALUES ('Chmielnik Pub', 5, 4.5, 5.0, 5.0, 5.0, 1800, true, 'Ambasador polskiego piwa w Poznaniu. Największy wybór piw z polskich browarów rzemieślniczych w samym sercu Poznania.  Zapewniamy wyjątkowy wystrój, profesjonalną obsługę, niepowtarzalny klimat. W sezonie letnim zapraszamy do magicznego ogrodu piwnego dla 200 osób, w którym główną atrakcją jest rosnący chmiel w odmianie SYBILLA oraz działająca kuchnia oferująca dania dopasowane do piwa.');
INSERT INTO public.restaurant (name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, description) VALUES ('Shipudei Berek', 6, 4.8, 4.0, 4.4, 4.7, 1800, true, 'Berek to rodzaj pocztówki z wakacji. Radość śródziemnomorskiego jedzenia i gorący klimat telawiwskich ulic, którego wspomnienie przeniesione zostało do Warszawy. Tworząc menu w Berku starałem się poznać lokalnych producentów i wykorzystywać jak najświeższe składniki. Wychowałem się w końcu w miejscu, gdzie owoce zjada się prosto z drzewa. Nasza pita jest wypiekana na miejscu i podawana na gorąco, hummus robimy taki, jaki sami jemy, korzystamy przede wszystkim z sezonowych warzyw. W Berku dzielę się moimi ulubionymi smakami domu, we wdzięcznym, współczesnym wydaniu.');
INSERT INTO public.restaurant (name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, description) VALUES ('Restauracja Pasja', 7, 4.5, 5.0, 5.0, 5.0, 1800, true, 'Zapewniamy wyjątkowy wystrój, profesjonalną obsługę, niepowtarzalny klimat. W sezonie letnim zapraszamy do magicznego ogrodu piwnego dla 200 osób, w którym główną atrakcją jest rosnący chmiel w odmianie SYBILLA oraz działająca kuchnia oferująca dania dopasowane do piwa.');

-- add users
INSERT INTO public.user (email, name, surname, restaurant_id, password, account_state) VALUES ('test@test.pl', 'Test', 'Testowy', 1, 'Test12345', 0);
INSERT INTO public.user (email, name, surname, restaurant_id, password, account_state) VALUES ('przemyslaw.pawlicki@gmail.com', 'Przemysław', 'Pawlicki', 2, 'password', 0);
INSERT INTO public.user (email, name, surname, restaurant_id, password, account_state) VALUES ('bartosz.zmarzlik@gmail.com', 'Bartosz', 'Zmarzlik', 3, 'password', 0);
INSERT INTO public.user (email, name, surname, restaurant_id, password, account_state) VALUES ('janusz.kolodziej@gmail.com', 'Janusz', 'Kołodziej', 4, 'password', 0);
INSERT INTO public.user (email, name, surname, restaurant_id, password, account_state) VALUES ('bartosz.smektala@gmail.com', 'Bartosz', 'Smektała', 5, 'password', 0);
INSERT INTO public.user (email, name, surname, restaurant_id, password, account_state) VALUES ('martyna.wierzbicka@gmail.com', 'Martyna', 'Wierzbicka', 6, 'password', 0);
INSERT INTO public.user (email, name, surname, restaurant_id, password, account_state) VALUES ('ofir.vidavsky@gmail.com', 'Ofir', 'Vidavsky', 7, 'password', 0);

-- add authorities
INSERT INTO public.user_authority (username, authority) VALUES (1, 'ROLE_OWNER');
INSERT INTO public.user_authority (username, authority) VALUES (7, 'ROLE_OWNER');
INSERT INTO public.user_authority (username, authority) VALUES (7, 'ROLE_ADMIN');

-- add kitchen types
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (1, 'POLISH');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (1, 'GREEK');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (2, 'ITALIAN');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (2, 'INDIAN');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (2, 'VIET_THAI');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (3, 'ASIAN');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (3, 'JAPANESE');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (3, 'VIET_THAI');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (4, 'BURGERS');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (4, 'MEXICAN');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (5, 'POLISH');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (6, 'VIET_THAI');

-- add business hours
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (0, '12:00:00', '21:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (1, '12:00:00', '21:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (2, '12:00:00', '21:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (3, '12:00:00', '21:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (4, '13:00:00', '23:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (5, '13:00:00', '23:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (6, '13:00:00', '20:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (1,1);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (1,2);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (1,3);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (1,4);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (1,5);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (1,6);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (1,7);

INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (0, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (1, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (2, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (3, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (4, '10:00:00', '22:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (5, '13:00:00', '22:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (6, '13:00:00', '22:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (2,8);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (2,9);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (2,10);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (2,11);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (2,12);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (2,13);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (2,14);

INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (0, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (1, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (2, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (3, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (4, '10:00:00', '22:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (5, '13:00:00', '22:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (6, '13:00:00', '22:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (3,15);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (3,16);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (3,17);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (3,18);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (3,19);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (3,20);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (3,21);

INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (0, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (1, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (2, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (3, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (4, '10:00:00', '22:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (5, '13:00:00', '22:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (6, '13:00:00', '22:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (4,22);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (4,23);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (4,24);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (4,25);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (4,26);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (4,27);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (4,28);

INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (0, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (1, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (2, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (3, '10:00:00', '19:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (4, '10:00:00', '22:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (5, '13:00:00', '22:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (6, '13:00:00', '22:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (5,29);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (5,30);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (5,31);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (5,32);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (5,33);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (5,34);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (5,35);

INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (0, '10:00:00', '21:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (1, '10:00:00', '21:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (2, '10:00:00', '21:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (3, '10:00:00', '21:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (4, '10:00:00', '23:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (5, '10:00:00', '23:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (6, '10:00:00', '23:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (6,36);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (6,37);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (6,38);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (6,39);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (6,40);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (6,41);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (6,42);

INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (0, '10:00:00', '21:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (1, '10:00:00', '21:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (2, '10:00:00', '21:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (3, '10:00:00', '21:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (4, '10:00:00', '23:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (5, '10:00:00', '23:00:00');
INSERT INTO public.business_hour (day_of_week, open_time, close_time) VALUES (6, '10:00:00', '23:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (7,43);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (7,44);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (7,45);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (7,46);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (7,47);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (7,48);
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id) VALUES (7,49);

-- add customers
INSERT INTO public.customer (email, phone_number, name, surname) VALUES ('karola.szafranska@gmail.pl', '666894323', 'Karola', 'Szafrańska');
INSERT INTO public.customer (email, phone_number, name, surname) VALUES ('maciej.wlodarczyk@gmail.pl', '883991105', 'Maciej', 'Włodarczyk');
INSERT INTO public.customer (email, phone_number, name, surname) VALUES ('sergiusz.malecki@gmail.pl', '739354158', 'Sergiusz', 'Małecki');
INSERT INTO public.customer (email, phone_number, name, surname) VALUES ('jagoda.fabisiak@gmail.pl', '789000731', 'Jagoda', 'Fabisiak');
INSERT INTO public.customer (email, phone_number, name, surname) VALUES ('maurycy.lisowski@gmail.pl', '729712835', 'Maurycy', 'Lisowski');

-- add spots
INSERT INTO public.spot (restaurant_id) VALUES (4, 1);
INSERT INTO public.spot (restaurant_id) VALUES (4, 1);
INSERT INTO public.spot (restaurant_id) VALUES (5, 1);
INSERT INTO public.spot (restaurant_id) VALUES (5, 1);
INSERT INTO public.spot (restaurant_id) VALUES (2, 1);
INSERT INTO public.spot (restaurant_id) VALUES (6, 2);
INSERT INTO public.spot (restaurant_id) VALUES (7, 2);
INSERT INTO public.spot (restaurant_id) VALUES (8, 2);
INSERT INTO public.spot (restaurant_id) VALUES (3, 2);
INSERT INTO public.spot (restaurant_id) VALUES (1, 2);

-- add reservations
INSERT INTO public.reservation (people_number, date, start_date_time, length, end_date_time, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (5, '2017-07-18', '2017-07-18 14:50:00.000', 1800, '2017-07-18 15:20:00.000', 0, 1, 1, 314251, true);
INSERT INTO public.reservation (people_number, date, start_date_time, length, end_date_time, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (15, '2017-07-18','2017-07-18 14:50:00.000', 1800, '2017-07-18 15:20:00.000', 0, 2, 2, 142512, true);
INSERT INTO public.reservation (people_number, date, start_date_time, length, end_date_time, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (7, '2017-07-18','2017-07-18 15:00:00.000', 1800, '2017-07-18 15:30:00.000', 0, 2, 1, 572456, true);
INSERT INTO public.reservation (people_number, date, start_date_time, length, end_date_time, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (3, '2017-07-18','2017-07-18 15:10:00.000', 1800, '2017-07-18 15:40:00.000', 0, 3, 1, 123683, true);
INSERT INTO public.reservation (people_number, date, start_date_time, length, end_date_time, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (5, '2017-07-18','2017-07-18 15:30:00.000', 1800, '2017-07-18 16:00:00.000', 0, 4, 1, 946784, true);
INSERT INTO public.reservation (people_number, date, start_date_time, length, end_date_time, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (20, '2017-07-18','2017-07-18 13:30:00.000', 1800, '2017-07-18 14:00:00.000', 0, 5, 1, 136578, false);
INSERT INTO public.reservation (people_number, date, start_date_time, length, end_date_time, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (5, '2017-07-19', '2017-07-19 14:50:00.000', 1800, '2017-07-19 15:20:00.000', 0, 1, 1, 846789, true);
/* @formatter:on */