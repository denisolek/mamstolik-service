/* @formatter:off */
ALTER TABLE public.restaurant ALTER COLUMN "description" TYPE VARCHAR(3000);

INSERT INTO public.authority (name) VALUES ('ROLE_OWNER');
INSERT INTO public.authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO public.authority (name) VALUES ('ROLE_EMPLOYEE');
INSERT INTO public.authority (name) VALUES ('ROLE_RESTAURANT');


INSERT INTO public.city (name) VALUES ('Poznań');
INSERT INTO public.city (name) VALUES ('Warszawa');
INSERT INTO public.city (name) VALUES ('Gorzów Wielkopolski');
INSERT INTO public.city (name) VALUES ('Gorzów Śląski');
INSERT INTO public.city (name) VALUES ('Gostyń');
INSERT INTO public.city (name) VALUES ('Gostynin');
INSERT INTO public.city (name) VALUES ('Pogorzela');
INSERT INTO public.city (name) VALUES ('Poręba');
INSERT INTO public.city (name) VALUES ('Płock');
INSERT INTO public.city (name) VALUES ('Wałcz');
INSERT INTO public.city (name) VALUES ('Włocławek');

-- city
INSERT INTO public.city_alias (name, city_id) VALUES ('pzn', 1);
INSERT INTO public.city_alias (name, city_id) VALUES ('wwa', 2);
INSERT INTO public.city_alias (name, city_id) VALUES ('gorzow', 3);
INSERT INTO public.city_alias (name, city_id) VALUES ('gorzow wlkp', 3);
INSERT INTO public.city_alias (name, city_id) VALUES ('gorzów wlkp', 3);
INSERT INTO public.city_alias (name, city_id) VALUES ('gorzów wlkp.', 3);
INSERT INTO public.city_alias (name, city_id) VALUES ('gw', 3);
INSERT INTO public.city_alias (name, city_id) VALUES ('gorzow', 4);
INSERT INTO public.city_alias (name, city_id) VALUES ('gorzow slaski', 4);

-- city
INSERT INTO public.address (building_number, latitude, longitude, postal_code, street_name, city_id) VALUES ('1A', 52.407640, 16.932010, '60-681', 'Półwiejska 42', 1);
INSERT INTO public.address (building_number, latitude, longitude, postal_code, street_name, city_id) VALUES ('2B', 52.409640, 16.32817, '60-681', 'Stary Rynek 55', 1);
INSERT INTO public.address (building_number, latitude, longitude, postal_code, street_name, city_id) VALUES ('3C', 52.407640, 16.932010, '60-681', 'Kwiatowa 3', 1);
INSERT INTO public.address (building_number, latitude, longitude, postal_code, street_name, city_id) VALUES ('4D', 52.312312, 16.932010, '60-681', 'Stary Rynek 100', 1);
INSERT INTO public.address (building_number, latitude, longitude, postal_code, street_name, city_id) VALUES ('5E', 52.407640, 16.932010, '60-681', 'Żydowska 27', 2);
INSERT INTO public.address (building_number, latitude, longitude, postal_code, street_name, city_id) VALUES ('6F', 52.407640, 16.932010, '60-681', 'Jasna 24', 2);
INSERT INTO public.address (building_number, latitude, longitude, postal_code, street_name, city_id) VALUES ('7G', 52.407640, 16.932010, '60-681', 'Batorego 82', 3);

-- add menu
INSERT INTO public.menu DEFAULT VALUES;

-- add users
INSERT INTO public.user (username, email, first_name, last_name, password, company_name, account_state) VALUES ('ms100000', 'test@test.pl', 'Właściciel', 'Właścicielowy', 'Test12345', 'MamStolik', 0);
INSERT INTO public.user (username, email, first_name, last_name, password, company_name, account_state) VALUES ('ms200000', 'przemyslaw.pawlicki@gmail.com', 'Przemysław', 'Pawlicki', 'password', 'Firmowa firma', 0);
INSERT INTO public.user (username, email, first_name, last_name, password, company_name, account_state) VALUES ('ms300000', 'bartosz.zmarzlik@gmail.com', 'Bartosz', 'Zmarzlik', 'password', 'Firmowa firma', 0);
INSERT INTO public.user (username, email, first_name, last_name, password, company_name, account_state) VALUES ('ms400000', 'janusz.kolodziej@gmail.com', 'Janusz', 'Kołodziej', 'password', 'Firmowa firma', 0);
INSERT INTO public.user (username, email, first_name, last_name, password, company_name, account_state) VALUES ('ms500000', 'bartosz.smektala@gmail.com', 'Bartosz', 'Smektała', 'password', 'Firmowa firma', 0);
INSERT INTO public.user (username, email, first_name, last_name, password, company_name, account_state) VALUES ('ms600000', 'martyna.wierzbicka@gmail.com', 'Martyna', 'Wierzbicka', 'password', 'Firmowa firma', 0);
INSERT INTO public.user (username, email, first_name, last_name, password, company_name, account_state) VALUES ('ms700000', 'ofir.vidavsky@gmail.com', 'Ofir', 'Vidavsky', 'password', 'Firmowa firma', 0);

INSERT INTO public.user (username, email, first_name, last_name, password, account_state) VALUES ('ms999999', 'admin@admin.pl', 'Admin', 'Adminujący', 'Test12345', 0);

-- address
INSERT INTO public.restaurant (name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, menu_id, owner_id, description) VALUES ('Piano Bar Restaurant & Cafe', 1, 4.5, 5.0, 5.0, 5.0, 1800, true, 1, 1, 'Piano Bar jest urokliwym, stylowym miejscem, które znajduje się w Centrum Sztuki i Biznesu w Starym Browarze. Jest niepowtarzalne i przepełnione niezwykłym klimatem. Piano Bar to restauracja, którą wyróżnia profesjonalna obsługa sprawiająca, że nasi Goście czują się naprawdę niezwykle. Dodatkowy, elegancki a zarazem ciepły wystrój wnętrza zapewnia uczucie wyjątkowości. Nasze smaki to kuchnia włoska, śródziemnomorska i potrawy kuchni polskiej. Szef Kuchni Krystian Szopka z zespołem kucharzy sprawia, że jej smak na długo pozostaje w pamięci naszych Gości. Zapraszamy na lunche, rodzinne obiady, spotkania biznesowe.');
INSERT INTO public.restaurant (name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, description) VALUES ('Ratuszova', 2, 4.5, 5.0, 5.0, 5.0, 1800, true, 'Restauracja Ratuszova serdecznie zaprasza w swoje progi wszystkich, którzy lubią lub pragną zasmakować tradycyjne potrawy kuchni polskiej! W naszym menu na szczególną uwagę zasługuje czernina z domowym makaronem, kaczka pieczona z jabłkami oraz dania z dziczyzny. Miłośników Slow Food, pasjonatów zdrowego odżywiania oraz wszystkich smakoszy w szczególności zachęcamy do spróbowania dań gotowanych innowacyjną metodą sous-vide. Dla jeszcze większego urozmaicenia nasze menu uzupełniliśmy smakami kuchni międzynarodowej. W naszej restauracji dbamy o to by sezonowo zmieniać potrawy oraz wystrój sal. ');
INSERT INTO public.restaurant (name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, description) VALUES ('Manekin', 3, 4.7, 4.0, 3.0, 3.5, 1800, true, 'Znana i ceniona sieć lokali podająca przepyszne naleśniki. Restauracja serwuje wiele rodzajów naleśników na słodko jak i na wytrawnie ale nie tylko. Tu można zjeść również sałatki, zupy, desery, spaghetti. Ogromnym atutem jest stylizowane wnętrze, nawiązujące do krainy baśniowej. W tym surrealistycznym otoczeniu można nie tylko zjeść, lecz również zrelaksować się przy kieliszku wina czy kuflu piwa.');
INSERT INTO public.restaurant (name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, description) VALUES ('Whiskey in the Jar', 4, 4.5, 5.0, 5.0, 5.0, 1800, true, 'Whiskey in the Jar to steakhouse, wzorowany na najlepszych amerykańskich lokalach tego typu. W menu znajdują się głównie dania mięsne – specjalnością są steki, przyrządzane z najwyższej jakości wołowiny, a także burgery i dania grillowane, dostępne pod nazwą Grill Rockersa. Wszystkie potrawy przygotowywane są na najdłuższym w Poznaniu grillu lawowym. Ogromnym powodzeniem cieszą się również autorskie drinki na bazie Jacka Daniels’a, zgodnie z nazwą lokalu podawane w designerskich słojach – jarach.');
INSERT INTO public.restaurant (name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, description) VALUES ('Chmielnik Pub', 5, 4.5, 5.0, 5.0, 5.0, 1800, true, 'Ambasador polskiego piwa w Poznaniu. Największy wybór piw z polskich browarów rzemieślniczych w samym sercu Poznania.  Zapewniamy wyjątkowy wystrój, profesjonalną obsługę, niepowtarzalny klimat. W sezonie letnim zapraszamy do magicznego ogrodu piwnego dla 200 osób, w którym główną atrakcją jest rosnący chmiel w odmianie SYBILLA oraz działająca kuchnia oferująca dania dopasowane do piwa.');
INSERT INTO public.restaurant (name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, description) VALUES ('Rapudei Berek', 6, 4.8, 4.0, 4.4, 4.7, 1800, true, 'Berek to rodzaj pocztówki z wakacji. Radość śródziemnomorskiego jedzenia i gorący klimat telawiwskich ulic, którego wspomnienie przeniesione zostało do Warszawy. Tworząc menu w Berku starałem się poznać lokalnych producentów i wykorzystywać jak najświeższe składniki. Wychowałem się w końcu w miejscu, gdzie owoce zjada się prosto z drzewa. Nasza pita jest wypiekana na miejscu i podawana na gorąco, hummus robimy taki, jaki sami jemy, korzystamy przede wszystkim z sezonowych warzyw. W Berku dzielę się moimi ulubionymi smakami domu, we wdzięcznym, współczesnym wydaniu.');
INSERT INTO public.restaurant (name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, description) VALUES ('Pasja', 7, 4.5, 5.0, 5.0, 5.0, 1800, true, 'Zapewniamy wyjątkowy wystrój, profesjonalną obsługę, niepowtarzalny klimat. W sezonie letnim zapraszamy do magicznego ogrodu piwnego dla 200 osób, w którym główną atrakcją jest rosnący chmiel w odmianie SYBILLA oraz działająca kuchnia oferująca dania dopasowane do piwa.');
INSERT INTO public.restaurant (name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, description) VALUES ('Cien', 7, 4.5, 5.0, 5.0, 5.0, 1800, false, 'Nie aktywna, bez miejsc, bez niczego');


INSERT INTO public.user (username, email, password, account_state, restaurant_id) VALUES ('ms100001', 'ms100001@mamstolik.pl', 'Test12345', 0, 1);
INSERT INTO public.user (username, email, first_name, last_name, password, phone_number, account_state, work_place_id) VALUES ('ms100002', 'ms100002@mamstolik.pl', 'Pracownik', 'Pracujący', 'Test12345', '507946148', 0, 1);
INSERT INTO public.user (username, email, first_name, last_name, password, phone_number, account_state, work_place_id) VALUES ('ms100003', 'ms100003@mamstolik.pl', 'Pracowniczka', 'Pracująca', 'Test12345', '603201114', 0, 1);


-- add authorities
INSERT INTO public.user_authority (username, authority) VALUES (2, 'ROLE_OWNER');
INSERT INTO public.user_authority (username, authority) VALUES (1, 'ROLE_OWNER');
INSERT INTO public.user_authority (username, authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.user_authority (username, authority) VALUES (9, 'ROLE_EMPLOYEE');
INSERT INTO public.user_authority (username, authority) VALUES (10, 'ROLE_EMPLOYEE');

-- restaurant
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (1, 'POLISH');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (1, 'ITALIAN');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (2, 'ITALIAN');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (2, 'SPANISH');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (2, 'PORTUGUESE');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (3, 'GREEK');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (3, 'FRENCH');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (3, 'ASIAN');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (4, 'VIETNAMESE');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (4, 'CHINESE');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (5, 'JAPANESE');
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type) VALUES (6, 'EUROPEAN');

-- restaurant
INSERT INTO public.restaurant_facility (restaurant_id, facility) VALUES (1, 'AIR_CONDITIONING');
INSERT INTO public.restaurant_facility (restaurant_id, facility) VALUES (1, 'SMOKING_ROOM');
INSERT INTO public.restaurant_facility (restaurant_id, facility) VALUES (2, 'NON_SMOKING_ROOM');
INSERT INTO public.restaurant_facility (restaurant_id, facility) VALUES (2, 'TERRACE');
INSERT INTO public.restaurant_facility (restaurant_id, facility) VALUES (2, 'GARDEN');
INSERT INTO public.restaurant_facility (restaurant_id, facility) VALUES (3, 'PARKING');
INSERT INTO public.restaurant_facility (restaurant_id, facility) VALUES (3, 'FREE_WIFI');
INSERT INTO public.restaurant_facility (restaurant_id, facility) VALUES (3, 'TOILET_FOR_DISABLED');
INSERT INTO public.restaurant_facility (restaurant_id, facility) VALUES (4, 'DISABLED_PEOPLE_IMPROVEMENTS');
INSERT INTO public.restaurant_facility (restaurant_id, facility) VALUES (4, 'CHILDREN_MENU');
INSERT INTO public.restaurant_facility (restaurant_id, facility) VALUES (5, 'CHILD_SEATS');
INSERT INTO public.restaurant_facility (restaurant_id, facility) VALUES (6, 'PLAYGROUND');

-- add business hours
INSERT INTO public.business_hour (open_time, close_time) VALUES ('12:00:00', '21:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('12:00:00', '21:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('12:00:00', '21:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('12:00:00', '21:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('13:00:00', '23:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('13:00:00', '23:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('13:00:00', '20:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (1,1, 'MONDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (1,2, 'TUESDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (1,3, 'WEDNESDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (1,4, 'THURSDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (1,5, 'FRIDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (1,6, 'SATURDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (1,7, 'SUNDAY');

INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '22:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('13:00:00', '22:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('13:00:00', '22:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (2,8, 'MONDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (2,9, 'TUESDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (2,10, 'WEDNESDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (2,11, 'THURSDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (2,12, 'FRIDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (2,13, 'SATURDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (2,14, 'SUNDAY');

INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '22:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('13:00:00', '22:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('13:00:00', '22:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (3,15, 'MONDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (3,16, 'TUESDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (3,17, 'WEDNESDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (3,18, 'THURSDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (3,19, 'FRIDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (3,20, 'SATURDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (3,21, 'SUNDAY');

INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '22:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('13:00:00', '22:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('13:00:00', '22:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (4,22, 'MONDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (4,23, 'TUESDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (4,24, 'WEDNESDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (4,25, 'THURSDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (4,26, 'FRIDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (4,27, 'SATURDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (4,28, 'SUNDAY');

INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '19:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '22:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('13:00:00', '22:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('13:00:00', '22:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (5,29, 'MONDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (5,30, 'TUESDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (5,31, 'WEDNESDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (5,32, 'THURSDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (5,33, 'FRIDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (5,34, 'SATURDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (5,35, 'SUNDAY');

INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '21:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '21:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '21:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '21:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '23:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '23:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '23:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (6,36, 'MONDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (6,37, 'TUESDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (6,38, 'WEDNESDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (6,39, 'THURSDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (6,40, 'FRIDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (6,41, 'SATURDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (6,42, 'SUNDAY');

INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '21:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '21:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '21:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '21:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '23:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '23:00:00');
INSERT INTO public.business_hour (open_time, close_time) VALUES ('10:00:00', '23:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (7, 43, 'MONDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (7, 44, 'TUESDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (7, 45, 'WEDNESDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (7, 46, 'THURSDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (7, 47, 'FRIDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (7, 48, 'SATURDAY');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week) VALUES (7, 49, 'SUNDAY');

-- add customers
INSERT INTO public.customer (email, phone_number, first_name, last_name) VALUES ('karola.szafranska@gmail.pl', '666894323', 'Karola', 'Szafrańska');
INSERT INTO public.customer (email, phone_number, first_name, last_name) VALUES ('maciej.wlodarczyk@gmail.pl', '883991105', 'Maciej', 'Włodarczyk');
INSERT INTO public.customer (email, phone_number, first_name, last_name) VALUES ('sergiusz.malecki@gmail.pl', '739354158', 'Sergiusz', 'Małecki');
INSERT INTO public.customer (email, phone_number, first_name, last_name) VALUES ('jagoda.fabisiak@gmail.pl', '789000731', 'Jagoda', 'Fabisiak');
INSERT INTO public.customer (email, phone_number, first_name, last_name) VALUES ('maurycy.lisowski@gmail.pl', '729712835', 'Maurycy', 'Lisowski');

-- add spots
INSERT INTO public.spot (capacity, restaurant_id) VALUES (4, 1);
INSERT INTO public.spot (capacity, restaurant_id) VALUES (4, 1);
INSERT INTO public.spot (capacity, restaurant_id) VALUES (5, 1);
INSERT INTO public.spot (capacity, restaurant_id) VALUES (5, 1);
INSERT INTO public.spot (capacity, restaurant_id) VALUES (2, 1);
INSERT INTO public.spot (capacity, restaurant_id, min_people_number) VALUES (6, 2, 3);
INSERT INTO public.spot (capacity, restaurant_id, min_people_number) VALUES (7, 2, 3);
INSERT INTO public.spot (capacity, restaurant_id, min_people_number) VALUES (8, 2, 4);
INSERT INTO public.spot (capacity, restaurant_id) VALUES (3, 2);
INSERT INTO public.spot (capacity, restaurant_id) VALUES (1, 2);

-- add reservations
INSERT INTO public.reservation (people_number, start_date_time, end_date_time, duration, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (5, '2017-10-18 14:50:00.000', '2017-10-18 15:20:00.000', 1800, 0, 1, 1, 314251, true);
INSERT INTO public.reservation (people_number, start_date_time, end_date_time, duration, state, customer_id, restaurant_id, verification_code, is_verified) VALUES (2, '2017-10-18 15:30:00.000', '2017-10-18 16:30:00.000', 3600, 0, 2, 1, 314251, true);

-- reservation spots
INSERT INTO public.reservation_spots (reservation_id, spots_id) VALUES (1, 3);
INSERT INTO public.reservation_spots (reservation_id, spots_id) VALUES (2, 4);

-- add menu category
INSERT INTO public.menu_category (menu_id, position, description, name) VALUES (1, 0, 'Tylko do godziny 12.00', 'Śniadania'); -- 1
INSERT INTO public.menu_category (menu_id, position, name) VALUES (1, 1, 'Przystawki'); -- 2
INSERT INTO public.menu_category (menu_id, position, name) VALUES (1, 2, 'Zupy'); -- 3
INSERT INTO public.menu_category (menu_id, position, name) VALUES (1, 3, 'Makarony'); -- 4
INSERT INTO public.menu_category (menu_id, position, name) VALUES (1, 4, 'Desery'); -- 5

-- add menu items
INSERT INTO public.menu_item (category_id, position, price, name, description) VALUES (1, 0, 9, 'Jajecznica z trzech jaj', 'Z chlebem wiejskim + boczek');
INSERT INTO public.menu_item (category_id, position, price, name, description) VALUES (1, 1, 11, 'Chałka z musem z koziego sera', 'Z miodem, orzechami laskowymi oraz rukolą');
INSERT INTO public.menu_item (category_id, position, price, name, description) VALUES (1, 2, 14, 'Jajko sadzone w chlebie', 'Z boczkiem, pieczarkami, pomidorem, serem oraz szpinakiem');
INSERT INTO public.menu_item (category_id, position, price, name, description) VALUES (1, 3, 12, 'Ser koryciński i hummus', 'Ze świeżymi warzywami, oliwkami i pieczywem');

INSERT INTO public.menu_item (category_id, position, price, name) VALUES (2, 0, 5, 'Zapiekany chlebek ze słonym masłem');
INSERT INTO public.menu_item (category_id, position, price, name) VALUES (2, 1, 10, 'Talrze serów');
INSERT INTO public.menu_item (category_id, position, price, name) VALUES (2, 2, 13, 'Melon zawijany szynką parmeńską');

INSERT INTO public.menu_item (category_id, position, price, name) VALUES (3, 0, 10, 'Krem z brokuł z grzankami');
INSERT INTO public.menu_item (category_id, position, price, name, description) VALUES (3, 1, 7, 'Pomidorowa', 'Z ryżem lub makaronem');
INSERT INTO public.menu_item (category_id, position, price, name, description) VALUES (3, 2, 9, 'Pieczarkowa', 'Z makaronem');

INSERT INTO public.menu_item (category_id, position, price, name) VALUES (4, 0, 14, 'Spaghetti bolognese');
INSERT INTO public.menu_item (category_id, position, price, name) VALUES (4, 1, 16, 'Spaghetti carbonara');
INSERT INTO public.menu_item (category_id, position, price, name) VALUES (4, 2, 18, 'Tagliatelle ze szpinakiem');

INSERT INTO public.menu_item (category_id, position, price, name) VALUES (5, 0, 10, 'Lody czekoladowe z owocami i bitą śmietaną');
INSERT INTO public.menu_item (category_id, position, price, name) VALUES (5, 1, 7, 'Suflet czekoladowy z malinami');
INSERT INTO public.menu_item (category_id, position, price, name) VALUES (5, 2, 9, 'Sernik z brzoskwinią');
/* @formatter:on */