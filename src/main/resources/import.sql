/* @formatter:off */
ALTER TABLE public.restaurant
  ALTER COLUMN "description" TYPE VARCHAR(3000);

INSERT INTO public.authority (role)
VALUES
  ('ROLE_OWNER'),
  ('ROLE_ADMIN'),
  ('ROLE_EMPLOYEE'),
  ('ROLE_MANAGER'),
  ('ROLE_RESTAURANT');

INSERT INTO public.city (name)
VALUES
  ('Poznań'),
  ('Warszawa'),
  ('Gorzów Wielkopolski'),
  ('Gorzów Śląski'),
  ('Gostyń'),
  ('Gostynin'),
  ('Pogorzela'),
  ('Poręba'),
  ('Płock'),
  ('Wałcz'),
  ('Włocławek');

-- city
INSERT INTO public.city_alias (name, city_id)
VALUES
  ('pzn', 1),
  ('wwa', 2),
  ('gorzow', 3),
  ('gorzow wlkp', 3),
  ('gorzów wlkp', 3),
  ('gorzów wlkp.', 3),
  ('gw', 3),
  ('gorzow', 4),
  ('gorzow slaski', 4);

-- city
INSERT INTO public.address (building_number, latitude, longitude, postal_code, street_name, city_id)
VALUES
  ('1A', 52.407640, 16.932010, '60-681', 'Półwiejska 42', 1),
  ('2B', 52.409640, 16.32817, '60-681', 'Stary Rynek 55', 1),
  ('3C', 52.407640, 16.932010, '60-681', 'Kwiatowa 3', 1),
  ('4D', 52.312312, 16.932010, '60-681', 'Stary Rynek 100', 1),
  ('5E', 52.407640, 16.932010, '60-681', 'Żydowska 27', 2),
  ('6F', 52.407640, 16.932010, '60-681', 'Jasna 24', 2),
  ('7G', 52.407640, 16.932010, '60-681', 'Batorego 82', 3);

-- add menu
INSERT INTO public.menu DEFAULT VALUES;

-- add users
INSERT INTO public.user (username, email, phone_number, first_name, last_name, password, company_name, account_state)
VALUES
  ('ms100000', 'test@test.pl', '509201391', 'Tomasz', 'Jabłoński', '$2a$10$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG', 'MamStolik', 'ACTIVE'),
  ('ms200000', 'przemyslaw.pawlicki@gmail.com', '602918372', 'Przemysław', 'Pawlicki', '$2a$10$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG', 'Firmowa firma', 'ACTIVE'),
  ('ms300000', 'bartosz.zmarzlik@gmail.com', '729392837', 'Bartosz', 'Zmarzlik', '$2a$10$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG', 'Firmowa firma', 'ACTIVE'),
  ('ms400000', 'janusz.kolodziej@gmail.com', '726349182', 'Janusz', 'Kołodziej', '$2a$10$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG', 'Firmowa firma', 'ACTIVE'),
  ('ms500000', 'bartosz.smektala@gmail.com', '692837163', 'Bartosz', 'Smektała', '$2a$10$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG', 'Firmowa firma', 'ACTIVE'),
  ('ms600000', 'martyna.wierzbicka@gmail.com', '613283756', 'Martyna', 'Wierzbicka', '$2a$10$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG', 'Firmowa firma', 'ACTIVE'),
  ('ms700000', 'ofir.vidavsky@gmail.com', '722351461', 'Ofir', 'Vidavsky', '$2a$10$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG', 'Firmowa firma', 'ACTIVE');

INSERT INTO public.user (username, email, first_name, last_name, password, account_state)
VALUES
  ('ms999999', 'admin@admin.pl', 'Admin', 'Adminujący', '$2a$10$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG', 'ACTIVE');

INSERT INTO public.user (username, email, first_name, last_name, activation_key, account_state)
VALUES
  ('ms800000', 'ms800000@test.pl', 'Test', 'Testowy', 'activationKeyTest', 'NOT_ACTIVE');

INSERT INTO public.settings(description, localization, menu, photos, schema, special_dates)
VALUES
  (TRUE, TRUE, TRUE, TRUE, TRUE, TRUE),
  (TRUE, TRUE, TRUE, TRUE, TRUE, TRUE),
  (TRUE, TRUE, TRUE, TRUE, TRUE, TRUE),
  (TRUE, TRUE, TRUE, TRUE, TRUE, TRUE),
  (TRUE, TRUE, TRUE, TRUE, TRUE, TRUE),
  (TRUE, TRUE, TRUE, TRUE, TRUE, TRUE),
  (TRUE, TRUE, TRUE, TRUE, TRUE, TRUE),
  (TRUE, TRUE, TRUE, TRUE, TRUE, TRUE);

-- address
INSERT INTO public.restaurant (name, url_name, address_id, rate, service_rate, food_rate, price_quality_rate, avg_reservation_time, is_active, menu_id, settings_id, owner_id, type, phone_number, email, description)
VALUES
  ('Piano Bar Restaurant & Cafe', 'piano.bar.restaurant.&.cafe', 1, 4.5, 5.0, 5.0, 5.0, 1800, TRUE, 1, 1, 1, 'RESTAURANT', '780199283', 'pianobar@gmail.com', 'Piano Bar jest urokliwym, stylowym miejscem, które znajduje się w Centrum Sztuki i Biznesu w Starym Browarze. Jest niepowtarzalne i przepełnione niezwykłym klimatem. Piano Bar to restauracja, którą wyróżnia profesjonalna obsługa sprawiająca, że nasi Goście czują się naprawdę niezwykle. Dodatkowy, elegancki a zarazem ciepły wystrój wnętrza zapewnia uczucie wyjątkowości. Nasze smaki to kuchnia włoska, śródziemnomorska i potrawy kuchni polskiej. Szef Kuchni Krystian Szopka z zespołem kucharzy sprawia, że jej smak na długo pozostaje w pamięci naszych Gości. Zapraszamy na lunche, rodzinne obiady, spotkania biznesowe.'),
  ('Ratuszova', 'ratuszova', 2, 4.5, 5.0, 5.0, 5.0, 1800, TRUE, NULL, 2, 2, 'BAR', '786395188', 'ratuszova@gmail.com', 'Restauracja Ratuszova serdecznie zaprasza w swoje progi wszystkich, którzy lubią lub pragną zasmakować tradycyjne potrawy kuchni polskiej! W naszym menu na szczególną uwagę zasługuje czernina z domowym makaronem, kaczka pieczona z jabłkami oraz dania z dziczyzny. Miłośników Slow Food, pasjonatów zdrowego odżywiania oraz wszystkich smakoszy w szczególności zachęcamy do spróbowania dań gotowanych innowacyjną metodą sous-vide. Dla jeszcze większego urozmaicenia nasze menu uzupełniliśmy smakami kuchni międzynarodowej. W naszej restauracji dbamy o to by sezonowo zmieniać potrawy oraz wystrój sal. '),
  ('Manekin', 'manekin',3, 4.7, 4.0, 3.0, 3.5, 1800, TRUE, NULL, 3, 3, 'RESTAURANT', '883597040', 'kontakt@manekin.pl', 'Znana i ceniona sieć lokali podająca przepyszne naleśniki. Restauracja serwuje wiele rodzajów naleśników na słodko jak i na wytrawnie ale nie tylko. Tu można zjeść również sałatki, zupy, desery, spaghetti. Ogromnym atutem jest stylizowane wnętrze, nawiązujące do krainy baśniowej. W tym surrealistycznym otoczeniu można nie tylko zjeść, lecz również zrelaksować się przy kieliszku wina czy kuflu piwa.'),
  ('Whiskey in the Jar', 'whiskey.in.the.jar', 4, 4.5, 5.0, 5.0, 5.0, 1800, TRUE, NULL, 4, 4, 'PUB', '536412108', 'whiskeyjar@gmail.com', 'Whiskey in the Jar to steakhouse, wzorowany na najlepszych amerykańskich lokalach tego typu. W menu znajdują się głównie dania mięsne – specjalnością są steki, przyrządzane z najwyższej jakości wołowiny, a także burgery i dania grillowane, dostępne pod nazwą Grill Rockersa. Wszystkie potrawy przygotowywane są na najdłuższym w Poznaniu grillu lawowym. Ogromnym powodzeniem cieszą się również autorskie drinki na bazie Jacka Daniels’a, zgodnie z nazwą lokalu podawane w designerskich słojach – jarach.'),
  ('Chmielnik Pub', 'chmielnik.pub',5, 4.5, 5.0, 5.0, 5.0, 1800, TRUE, NULL, 5, 5, 'RESTAURANT', '881084469', 'kontakt@chmielnik.pl', 'Ambasador polskiego piwa w Poznaniu. Największy wybór piw z polskich browarów rzemieślniczych w samym sercu Poznania.  Zapewniamy wyjątkowy wystrój, profesjonalną obsługę, niepowtarzalny klimat. W sezonie letnim zapraszamy do magicznego ogrodu piwnego dla 200 osób, w którym główną atrakcją jest rosnący chmiel w odmianie SYBILLA oraz działająca kuchnia oferująca dania dopasowane do piwa.'),
  ('Rapudei Berek', 'rapudei.berek',6, 4.8, 4.0, 4.4, 4.7, 1800, TRUE, NULL, 6, 6, 'RESTAURANT', '727000890', 'berekrestaurant@gmail.com', 'Berek to rodzaj pocztówki z wakacji. Radość śródziemnomorskiego jedzenia i gorący klimat telawiwskich ulic, którego wspomnienie przeniesione zostało do Warszawy. Tworząc menu w Berku starałem się poznać lokalnych producentów i wykorzystywać jak najświeższe składniki. Wychowałem się w końcu w miejscu, gdzie owoce zjada się prosto z drzewa. Nasza pita jest wypiekana na miejscu i podawana na gorąco, hummus robimy taki, jaki sami jemy, korzystamy przede wszystkim z sezonowych warzyw. W Berku dzielę się moimi ulubionymi smakami domu, we wdzięcznym, współczesnym wydaniu.'),
  ('Pasja', 'pasja', 7, 4.5, 5.0, 5.0, 5.0, 1800, TRUE, NULL, 7, 8, 'RESTAURANT', '575718545', 'pasja@pasja.com', 'Zapewniamy wyjątkowy wystrój, profesjonalną obsługę, niepowtarzalny klimat. W sezonie letnim zapraszamy do magicznego ogrodu piwnego dla 200 osób, w którym główną atrakcją jest rosnący chmiel w odmianie SYBILLA oraz działająca kuchnia oferująca dania dopasowane do piwa.'),
  ('Cien', 'cien',7, 4.5, 5.0, 5.0, 5.0, 1800, FALSE, NULL, 8, 9, 'RESTAURANT', '666897683', 'cien@cien.pl', 'Nie aktywna, bez miejsc, bez niczego');

INSERT INTO public.user (username, email, password, account_state, restaurant_id)
VALUES
  ('ms100001', 'ms100001@mamstolik.pl', '$2a$10$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG', 'ACTIVE', 1);

INSERT INTO public.user (username, email, work_email, first_name, last_name, password, phone_number, account_state, work_place_id)
VALUES
  ('ms100002', 'ms100002@mamstolik.pl', 'ms100002@mamstolik.pl', 'Krystian', 'Nowicki', '$2a$10$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG', '507946148', 'ACTIVE', 1),
  ('ms100003', 'ms100003@mamstolik.pl', 'ms100002@mamstolik.pl', 'Magdalena', 'Karpińska', '$2a$10$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG', '603201114', 'ACTIVE', 1);

-- add authorities
INSERT INTO public.user_authority (username, authority)
VALUES
  (1, 'ROLE_OWNER'),
  (1, 'ROLE_ADMIN'),
  (2, 'ROLE_OWNER'),
  (3, 'ROLE_OWNER'),
  (4, 'ROLE_OWNER'),
  (5, 'ROLE_OWNER'),
  (6, 'ROLE_OWNER'),
  (7, 'ROLE_OWNER'),
  (9, 'ROLE_EMPLOYEE'),
  (10, 'ROLE_RESTAURANT'),
  (11, 'ROLE_EMPLOYEE'),
  (11, 'ROLE_MANAGER'),
  (12, 'ROLE_EMPLOYEE');

-- restaurant
INSERT INTO public.restaurant_kitchen (restaurant_id, kitchen_type)
VALUES
  (1, 'POLISH'),
  (1, 'ITALIAN'),
  (2, 'ITALIAN'),
  (2, 'SPANISH'),
  (2, 'PORTUGUESE'),
  (3, 'GREEK'),
  (3, 'FRENCH'),
  (3, 'ASIAN'),
  (4, 'VIETNAMESE'),
  (4, 'CHINESE'),
  (5, 'JAPANESE'),
  (6, 'EUROPEAN');

-- restaurant
INSERT INTO public.restaurant_facility (restaurant_id, facility)
VALUES
  (1, 'AIR_CONDITIONING'),
  (1, 'SMOKING_ROOM'),
  (2, 'NON_SMOKING_ROOM'),
  (2, 'TERRACE'),
  (2, 'GARDEN'),
  (3, 'PARKING'),
  (3, 'FREE_WIFI'),
  (3, 'TOILET_FOR_DISABLED'),
  (4, 'DISABLED_PEOPLE_IMPROVEMENTS'),
  (4, 'CHILDREN_MENU'),
  (5, 'CHILD_SEATS'),
  (6, 'PLAYGROUND');

-- add business hours
INSERT INTO public.business_hour (open_time, close_time, is_closed)
VALUES
  ('12:00:00', '21:00:00', FALSE),
  ('12:00:00', '21:00:00', FALSE),
  ('12:00:00', '21:00:00', FALSE),
  ('12:00:00', '21:00:00', FALSE),
  ('13:00:00', '23:00:00', FALSE),
  ('13:00:00', '23:00:00', FALSE),
  ('13:00:00', '20:00:00', FALSE);

INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week)
VALUES
  (1, 1, 'MONDAY'),
  (1, 2, 'TUESDAY'),
  (1, 3, 'WEDNESDAY'),
  (1, 4, 'THURSDAY'),
  (1, 5, 'FRIDAY'),
  (1, 6, 'SATURDAY'),
  (1, 7, 'SUNDAY');

INSERT INTO public.business_hour (open_time, close_time)
VALUES
  ('10:00:00', '19:00:00'),
  ('10:00:00', '19:00:00'),
  ('10:00:00', '19:00:00'),
  ('10:00:00', '19:00:00'),
  ('10:00:00', '22:00:00'),
  ('13:00:00', '22:00:00'),
  ('13:00:00', '22:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week)
VALUES
  (2, 8, 'MONDAY'),
  (2, 9, 'TUESDAY'),
  (2, 10, 'WEDNESDAY'),
  (2, 11, 'THURSDAY'),
  (2, 12, 'FRIDAY'),
  (2, 13, 'SATURDAY'),
  (2, 14, 'SUNDAY');

INSERT INTO public.business_hour (open_time, close_time)
VALUES
  ('10:00:00', '19:00:00'),
  ('10:00:00', '19:00:00'),
  ('10:00:00', '19:00:00'),
  ('10:00:00', '19:00:00'),
  ('10:00:00', '22:00:00'),
  ('13:00:00', '22:00:00'),
  ('13:00:00', '22:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week)
VALUES
  (3, 15, 'MONDAY'),
  (3, 16, 'TUESDAY'),
  (3, 17, 'WEDNESDAY'),
  (3, 18, 'THURSDAY'),
  (3, 19, 'FRIDAY'),
  (3, 20, 'SATURDAY'),
  (3, 21, 'SUNDAY');

INSERT INTO public.business_hour (open_time, close_time)
VALUES
  ('10:00:00', '19:00:00'),
  ('10:00:00', '19:00:00'),
  ('10:00:00', '19:00:00'),
  ('10:00:00', '19:00:00'),
  ('10:00:00', '22:00:00'),
  ('13:00:00', '22:00:00'),
  ('13:00:00', '22:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week)
VALUES
  (4, 22, 'MONDAY'),
  (4, 23, 'TUESDAY'),
  (4, 24, 'WEDNESDAY'),
  (4, 25, 'THURSDAY'),
  (4, 26, 'FRIDAY'),
  (4, 27, 'SATURDAY'),
  (4, 28, 'SUNDAY');

INSERT INTO public.business_hour (open_time, close_time)
VALUES
  ('10:00:00', '19:00:00'),
  ('10:00:00', '19:00:00'),
  ('10:00:00', '19:00:00'),
  ('10:00:00', '19:00:00'),
  ('10:00:00', '22:00:00'),
  ('13:00:00', '22:00:00'),
  ('13:00:00', '22:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week)
VALUES
  (5, 29, 'MONDAY'),
  (5, 30, 'TUESDAY'),
  (5, 31, 'WEDNESDAY'),
  (5, 32, 'THURSDAY'),
  (5, 33, 'FRIDAY'),
  (5, 34, 'SATURDAY'),
  (5, 35, 'SUNDAY');

INSERT INTO public.business_hour (open_time, close_time)
VALUES
  ('10:00:00', '21:00:00'),
  ('10:00:00', '21:00:00'),
  ('10:00:00', '21:00:00'),
  ('10:00:00', '21:00:00'),
  ('10:00:00', '23:00:00'),
  ('10:00:00', '23:00:00'),
  ('10:00:00', '23:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week)
VALUES
  (6, 36, 'MONDAY'),
  (6, 37, 'TUESDAY'),
  (6, 38, 'WEDNESDAY'),
  (6, 39, 'THURSDAY'),
  (6, 40, 'FRIDAY'),
  (6, 41, 'SATURDAY'),
  (6, 42, 'SUNDAY');

INSERT INTO public.business_hour (open_time, close_time)
VALUES
  ('10:00:00', '21:00:00'),
  ('10:00:00', '21:00:00'),
  ('10:00:00', '21:00:00'),
  ('10:00:00', '21:00:00'),
  ('10:00:00', '23:00:00'),
  ('10:00:00', '23:00:00'),
  ('10:00:00', '23:00:00');
INSERT INTO public.restaurant_business_hour (restaurant_id, business_hour_id, day_of_week)
VALUES
  (7, 43, 'MONDAY'),
  (7, 44, 'TUESDAY'),
  (7, 45, 'WEDNESDAY'),
  (7, 46, 'THURSDAY'),
  (7, 47, 'FRIDAY'),
  (7, 48, 'SATURDAY'),
  (7, 49, 'SUNDAY');

-- add customers
INSERT INTO public.customer (email, phone_number, first_name, last_name)
VALUES
  ('karola.szafranska@gmail.pl', '666894323', 'Karola', 'Szafrańska'),
  ('maciej.wlodarczyk@gmail.pl', '883991105', 'Maciej', 'Włodarczyk'),
  ('sergiusz.malecki@gmail.pl', '739354158', 'Sergiusz', 'Małecki'),
  ('jagoda.fabisiak@gmail.pl', '789000731', 'Jagoda', 'Fabisiak'),
  ('maurycy.lisowski@gmail.pl', '729712835', 'Maurycy', 'Lisowski');

-- add spots
INSERT INTO public.spot (capacity, number,restaurant_id, min_people_number)
VALUES
  (4, 1, 1, 1), -- 1
  (4, 2, 1, 1), -- 2
  (5, 3, 1, 1), -- 3
  (5, 4, 1, 1), -- 4
  (2, 5, 1, 1), -- 5
  (4, 6, 1, 1), -- 6
  (4, 7, 1, 1), -- 7
  (5, 8, 1, 1), -- 8
  (5, 9, 1, 1), -- 9
  (2, 10, 1, 1), -- 10
  (6, 11, 1, 3), -- 11
  (7, 12, 1, 3), -- 12
  (8, 13, 1, 4), -- 13
  (6, 14, 1, 3), -- 14
  (7, 15, 1, 3), -- 15
  (8, 16, 1, 4), -- 16
  (4, 17, 1, 1), -- 17
  (4, 18, 1, 1), -- 18
  (5, 19, 1, 1), -- 19
  (5, 20, 1, 1), -- 20
  (2, 21, 1, 1), -- 21
  (4, 22, 1, 1), -- 22
  (6, 23, 2, 3), -- 23
  (7, 1, 2, 3), -- 24
  (8, 2, 2, 4), -- 25
  (3, 3, 2, 1), -- 26
  (1, 4, 2, 1); -- 27

-- add floors
INSERT INTO public.floor (name, restaurant_id)
VALUES
  ('Parter', 1),
  ('Pierwsze piętro', 1),
  ('Taras', 1);

-- add schema items
INSERT INTO public.schema_item (type, table_type, item_type, wall_item_type, floor_id, width, height, rotation, x, y, spot_id)
VALUES
  ('TABLE', 'FOUR_RECT', NULL, NULL, 1, 200, 200, 0.0, 238.33334859212255, 292.1991639196625, 1),
  ('TABLE', 'FOUR_RECT', NULL, NULL, 1, 200, 200, 0.0, 1443.3333740234375, 1377.764169088224, 2),
  ('TABLE', 'FIVE_ROUND', NULL, NULL, 1, 200, 200, 0.0, 1320.8264311483083, 2583.333333333333, 3),
  ('TABLE', 'FIVE_RECT_1', NULL, NULL, 1, 200, 200, 0.0, 1890.4405693244653, 1900.000040690104, 4),
  ('TABLE', 'TWO_ROUND', NULL, NULL, 1, 200, 200, 0.0, 1898.7465157837248, 2293.333251953125, 5),
  ('TABLE', 'FOUR', NULL, NULL, 1, 200, 200, 0.0, 2376.666748046875, 2307.079971187371, 6),
  ('TABLE', 'FOUR', NULL, NULL, 1, 200, 200, 0.0, 2353.8028982816168, 1386.6666259765625, 7),
  ('TABLE', 'FIVE_RECT_2', NULL, NULL, 1, 200, 200, 0.0, 1518.3333333333335, 2235.7952159918527, 8),
  ('TABLE', 'FIVE_RECT_3', NULL, NULL, 1, 200, 200, 0.0, 685, 299.99998982747394, 9),
  ('TABLE', 'TWO', NULL, NULL, 1, 200, 200, 0.0, 2416.666666666667, 1016.6666463216147, 10),
  ('TABLE', 'SIX_RECT_1', NULL, NULL, 1, 200, 200, 0.0, 1856.6666666666665, 1409.01273515194, 11),
  ('TABLE', 'EIGHT_RECT', NULL, NULL, 1, 200, 200, 0.0, 1899.999959309896, 906.6666463216145, 12),
  ('TABLE', 'EIGHT_ROUND', NULL, NULL, 1, 200, 200, 0.0, 1481.666666666667, 1874.9998372395835, 13),
  ('TABLE', 'SIX_RECT_2', NULL, NULL, 1, 200, 200, 0.0, 197.032499980151, 2191.6665852864585, 14),
  ('TABLE', 'EIGHT_ROUND', NULL, NULL, 1, 200, 200, 0.0, 2400, 563.1104153185751, 15),
  ('TABLE', 'EIGHT_RECT', NULL, NULL, 1, 200, 200, 0.0, 1891.2096774193549, 536.6666463216146, 16),
  ('TABLE', 'FOUR', NULL, NULL, 1, 200, 200, 0.0, 1068.3333536783857, 1372.731909280991, 17),
  ('TABLE', 'FOUR', NULL, NULL, 1, 200, 200, 0.0, 600.0000203450518, 2584.065153788745, 18),
  ('TABLE', 'FIVE_ROUND', NULL, NULL, 1, 200, 200, 0.0, 219.1597848266938, 2575.0000813802085, 19),
  ('TABLE', 'FIVE_RECT_3', NULL, NULL, 1, 200, 200, 0.0, 2415.4406100145698, 1910, 20),
  ('TABLE', 'TWO_ROUND', NULL, NULL, 1, 200, 200, 0.0, 237.4931130737641, 731.6666870117188, 21),
  ('TABLE', 'FOUR_RECT', NULL, NULL, 1, 200, 200, 0.0, 268.33332316080737, 1333.7465971639333, 22),
  ('ITEM', NULL, 'TOILET', NULL, 1, 200, 200, 0.0, 1280, 1070, NULL),
  ('WALL', NULL, NULL, NULL, 1, 313.33333333333326, 50, 0.0, 1140, 598.3333333333334, NULL),
  ('WALL', NULL, NULL, NULL, 1, 366.66666666666697, 50, 0.0, 1093.3333333333326, 393.33333333333326, NULL),
  ('WALL', NULL, NULL, NULL, 1, 236.66666666666669, 50, 90, 1025.19871760124, 529.9917645517912, NULL),
  ('WALL', NULL, NULL, NULL, 1, 903.3333333333333, 50, 0.000018899085304724395, 1795.0000000000005, 2540.0000000000005, NULL),
  ('WALL', NULL, NULL, NULL, 1, 2128.333333333333, 50, 90, 1637.5001948734991, 1522.4999999999766, NULL),
  ('WALL', NULL, NULL, NULL, 1, 911.6666666666669, 50, 0.0, 1789.9999999999998, 455.00000000000006, NULL),
  ('WALL', NULL, NULL, NULL, 1, 873.3333333333333, 50, 0.0, 940.0000000000001, 986.6666666666666, NULL),
  ('WALL', NULL, NULL, NULL, 1, 870, 50, 0.0, 941.6666666666665, 1295.0000000000002, NULL),
  ('WALL', NULL, NULL, NULL, 1, 1655, 50, 90.0, 162.49988203515016, 1029.1666666666772, NULL),
  ('WALL', NULL, NULL, NULL, 1, 863.3333333333334, 50, 0.0, 103.33333333333326, 1615, NULL),
  ('WALL', NULL, NULL, NULL, 1, 1720.0000000000002, 50, 0.0, 105.00000000000003, 193.33333333333331, NULL),
  ('WALL', NULL, NULL, NULL, 1, 2638.3333333333335, 50, 90.0, 506.7980929598966, 1519.2202287944276, NULL),
  ('WALL', NULL, NULL, NULL, 1, 1704.9999999999998, 50, 0.0, 106.66666666666657, 2106.6666666666665, NULL),
  ('WALL', NULL, NULL, NULL, 1, 2645, 50, 90.0, -1176.555583852268, 1515.8603400051134, NULL),
  ('WALL', NULL, NULL, NULL, 1, 1708.3333333333333, 50, 0.0, 106.66666666666674, 2788.333333333333, NULL),
  ('WALL_ITEM', NULL, NULL, 'WINDOW', 1, 450, 48, 0.0, 200, 195, NULL),
  ('WALL_ITEM', NULL, NULL, 'WINDOW', 1, 450, 48, 90, -79.99999999999977, 2468.3333333333326, NULL),
  ('WALL_ITEM', NULL, NULL, 'DOOR', 1, 350, 48, 0.0, 1053.333333333333, 2111.666666666667, NULL),
  ('WALL_ITEM', NULL, NULL, 'DOOR', 1, 350, 48, 90, -30, 1891.6666666666665, NULL),
  ('WALL_ITEM', NULL, NULL, 'DOOR', 1, 350, 48, 90, 1649.9999999999998, 1694.9999999999998, NULL),
  ('WALL_ITEM', NULL, NULL, 'DOOR', 1, 350, 48, 270, 770.0000000000002, 815, NULL),
  ('WALL_ITEM', NULL, NULL, 'DOOR', 1, 350, 48, 0.0, 393.3333333333335, 1618.3333333333333, NULL),
  ('WALL_ITEM', NULL, NULL, 'WINDOW', 1, 450, 48, 90, -76.66666666666629, 1168.3333333333333, NULL),
  ('WALL_ITEM', NULL, NULL, 'WINDOW', 1, 450, 48, 0.0, 1166.6666666666665, 198.33333333333343, NULL),
  ('WALL_ITEM', NULL, NULL, 'WINDOW', 1, 450, 48, 0.0, 823.3333333333333, 2791.6666666666665, NULL),
  ('WALL_ITEM', NULL, NULL, 'WINDOW', 1, 450, 48, 90, 2476.6666666666665, 1878.333333333333, NULL),
  ('WALL_ITEM', NULL, NULL, 'WINDOW', 1, 450, 48, 90, 2476.6666666666665, 918.3333333333335, NULL),
  ('ITEM', NULL, 'TOILET', NULL, 2, 200, 200, 0.0, 1280, 1070, NULL),
  ('ITEM', NULL, 'STAIRS', NULL, 2, 200, 200, 0.0, 2416.666666666667, 1016.6666463216147, NULL),
  ('WALL', NULL, NULL, NULL, 2, 313.33333333333326, 50, 0.0, 1140, 598.3333333333334, NULL),
  ('WALL', NULL, NULL, NULL, 2, 366.66666666666697, 50, 0.0, 1093.3333333333326, 393.33333333333326, NULL),
  ('WALL', NULL, NULL, NULL, 2, 236.66666666666669, 50, 90, 1025.19871760124, 529.9917645517912, NULL),
  ('WALL', NULL, NULL, NULL, 2, 903.3333333333333, 50, 0.000018899085304724395, 1795.0000000000005, 2540.0000000000005, NULL),
  ('WALL', NULL, NULL, NULL, 2, 2128.333333333333, 50, 90, 1637.5001948734991, 1522.4999999999766, NULL),
  ('WALL', NULL, NULL, NULL, 2, 911.6666666666669, 50, 0.0, 1789.9999999999998, 455.00000000000006, NULL),
  ('WALL', NULL, NULL, NULL, 2, 873.3333333333333, 50, 0.0, 940.0000000000001, 986.6666666666666, NULL),
  ('WALL', NULL, NULL, NULL, 2, 870, 50, 0.0, 941.6666666666665, 1295.0000000000002, NULL),
  ('WALL', NULL, NULL, NULL, 2, 1655, 50, 90.0, 162.49988203515016, 1029.1666666666772, NULL),
  ('WALL', NULL, NULL, NULL, 2, 863.3333333333334, 50, 0.0, 103.33333333333326, 1615, NULL),
  ('WALL', NULL, NULL, NULL, 2, 1720.0000000000002, 50, 0.0, 105.00000000000003, 193.33333333333331, NULL),
  ('WALL', NULL, NULL, NULL, 2, 2638.3333333333335, 50, 90.0, 506.7980929598966, 1519.2202287944276, NULL),
  ('WALL', NULL, NULL, NULL, 2, 1704.9999999999998, 50, 0.0, 106.66666666666657, 2106.6666666666665, NULL),
  ('WALL', NULL, NULL, NULL, 2, 2645, 50, 90.0, -1176.555583852268, 1515.8603400051134, NULL),
  ('WALL', NULL, NULL, NULL, 2, 1708.3333333333333, 50, 0.0, 106.66666666666674, 2788.333333333333, NULL),
  ('WALL_ITEM', NULL, NULL, 'WINDOW', 2, 450, 48, 0.0, 200, 195, NULL),
  ('WALL_ITEM', NULL, NULL, 'WINDOW', 2, 450, 48, 90, -79.99999999999977, 2468.3333333333326, NULL),
  ('WALL_ITEM', NULL, NULL, 'DOOR', 2, 350, 48, 0.0, 1053.333333333333, 2111.666666666667, NULL),
  ('WALL_ITEM', NULL, NULL, 'DOOR', 2, 350, 48, 90, -30, 1891.6666666666665, NULL),
  ('WALL_ITEM', NULL, NULL, 'DOOR', 2, 350, 48, 90, 1649.9999999999998, 1694.9999999999998, NULL),
  ('WALL_ITEM', NULL, NULL, 'DOOR', 2, 350, 48, 270, 770.0000000000002, 815, NULL),
  ('WALL_ITEM', NULL, NULL, 'DOOR', 2, 350, 48, 0.0, 393.3333333333335, 1618.3333333333333, NULL),
  ('WALL_ITEM', NULL, NULL, 'WINDOW', 2, 450, 48, 90, -76.66666666666629, 1168.3333333333333, NULL),
  ('WALL_ITEM', NULL, NULL, 'WINDOW', 2, 450, 48, 0.0, 1166.6666666666665, 198.33333333333343, NULL),
  ('WALL_ITEM', NULL, NULL, 'WINDOW', 2, 450, 48, 0.0, 823.3333333333333, 2791.6666666666665, NULL),
  ('WALL_ITEM', NULL, NULL, 'WINDOW', 2, 450, 48, 90, 2476.6666666666665, 1878.333333333333, NULL),
  ('WALL_ITEM', NULL, NULL, 'WINDOW', 2, 450, 48, 90, 2476.6666666666665, 918.3333333333335, NULL);

-- add reservations
INSERT INTO public.reservation (people_number, start_date_time, end_date_time, duration, state, customer_id, restaurant_id, verification_code, is_verified)
VALUES
  (5, '2017-10-18 14:45:00.000', '2017-10-18 15:15:00.000', 1800, 0, 1, 1, 314251, TRUE),
  (2, '2017-10-18 15:30:00.000', '2017-10-18 16:30:00.000', 3600, 0, 2, 1, 314251, TRUE),
  (3, '2018-10-18 15:30:00.000', '2018-10-18 16:30:00.000', 3600, 0, 2, 1, 314251, TRUE),
  (3, '2018-10-19 15:30:00.000', '2018-10-19 16:30:00.000', 3600, 0, 2, 1, 314251, TRUE),
  (3, '2018-10-20 15:30:00.000', '2018-10-20 16:30:00.000', 3600, 0, 2, 1, 314251, TRUE);

-- reservation spots
INSERT INTO public.reservation_spots (reservation_id, spot_id)
VALUES
  (1, 3),
  (2, 4),
  (3, 3),
  (4, 4),
  (5, 3);

-- add menu category
INSERT INTO public.menu_category (menu_id, position, description, name)
VALUES
  (1, 0, 'Tylko do godziny 12.00', 'Śniadania'), -- 1
  (1, 1, NULL, 'Przystawki'), -- 2
  (1, 2, NULL, 'Zupy'), -- 3
  (1, 3, NULL, 'Makarony'), -- 4
  (1, 4, NULL, 'Desery'); -- 5

-- add menu items
INSERT INTO public.menu_item (category_id, position, price, name, description)
VALUES
  (1, 0, 9, 'Jajecznica z trzech jaj', 'Z chlebem wiejskim + boczek'),
  (1, 1, 11, 'Chałka z musem z koziego sera', 'Z miodem, orzechami laskowymi oraz rukolą'),
  (1, 2, 14, 'Jajko sadzone w chlebie', 'Z boczkiem, pieczarkami, pomidorem, serem oraz szpinakiem'),
  (1, 3, 12, 'Ser koryciński i hummus', 'Ze świeżymi warzywami, oliwkami i pieczywem'),
  (2, 0, 5, 'Zapiekany chlebek ze słonym masłem', NULL),
  (2, 1, 10, 'Talrze serów', NULL),
  (2, 2, 13, 'Melon zawijany szynką parmeńską', NULL),
  (3, 0, 10, 'Krem z brokuł z grzankami', NULL),
  (3, 1, 7, 'Pomidorowa', 'Z ryżem lub makaronem'),
  (3, 2, 9, 'Pieczarkowa', 'Z makaronem'),
  (4, 0, 14, 'Spaghetti bolognese', NULL),
  (4, 1, 16, 'Spaghetti carbonara', NULL),
  (4, 2, 18, 'Tagliatelle ze szpinakiem', NULL),
  (5, 0, 10, 'Lody czekoladowe z owocami i bitą śmietaną', NULL),
  (5, 1, 7, 'Suflet czekoladowy z malinami', NULL),
  (5, 2, 9, 'Sernik z brzoskwinią', NULL);
/* @formatter:on */