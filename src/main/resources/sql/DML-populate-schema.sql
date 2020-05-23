USE nordic_motorhome_rental;

SELECT * FROM departments;
SELECT * FROM users;
SELECT * FROM authorities;
SELECT * FROM employees;
SELECT * FROM accessories;
SELECT * FROM autocampers;
SELECT * FROM bookings;

INSERT INTO departments
(id, name)
VALUES
(DEFAULT, "Bestyrelse"),
(DEFAULT, "Salg"),
(DEFAULT, "Rengøring"),
(DEFAULT, "Bilservice"),
(DEFAULT, "Bogholderi");

INSERT INTO users
(username, password, enabled)
VALUES
("Ejer1", "ejer1", TRUE),
("Salg1", "salg1", TRUE),
("Salg2", "salg2", TRUE),
("Salg3", "salg3", TRUE),
("Rengøring1", "rengøring1", TRUE),
("Mekaniker1", "mekaniker1", TRUE),
("Bogholder1", "bogholder1", TRUE);

INSERT INTO authorities
(username, authority)
VALUES
("Ejer1", "ROLE_ADMIN"),
("Salg1", "ROLE_SALG"),
("Salg2", "ROLE_SALG"),
("Salg3", "ROLE_SALG"),
("Rengøring1", "ROLE_RENGØRING"),
("Mekaniker1", "ROLE_MEKANIKER"),
("Bogholder1", "ROLE_BOGHOLDER");

INSERT INTO employees
(id, department_id, users_username, first_name, last_name, phone, mail, zip_code, city, address)
VALUES
(DEFAULT, (SELECT id FROM departments WHERE name = "Bestyrelse"), "Ejer1", "David", "Harry Ema", 46460400, "de@kea.dk", 2200, "København N", "Lygten 37"),
(DEFAULT, (SELECT id FROM departments WHERE name = "Salg"), "Salg1", "Salg", "Test1", 00000000, "salg@nordicmotorhome.dk", 2200, "København N", "Lygten 37"),
(DEFAULT, (SELECT id FROM departments WHERE name = "Salg"), "Salg2", "Salg", "Test2", 00000000, "salg@nordicmotorhome.dk", 2200, "København N", "Lygten 37"),
(DEFAULT, (SELECT id FROM departments WHERE name = "Salg"), "Salg3", "Salg", "Test3", 00000000, "salg@nordicmotorhome.dk", 2200, "København N", "Lygten 37"),
(DEFAULT, (SELECT id FROM departments WHERE name = "Rengøring"), "Rengøring1", "Rengøring", "Test1", 00000000, "rengøring@nordicmotorhome.dk", 2200, "København N", "Lygten 37"),
(DEFAULT, (SELECT id FROM departments WHERE name = "Bilservice"), "Mekaniker1", "Mekaniker", "Test1", 00000000, "bilservice@nordicmotorhome.dk", 2200, "København N", "Lygten 37"),
(DEFAULT, (SELECT id FROM departments WHERE name = "Bogholderi"), "Bogholder1", "Bogholder", "Test1", 00000000, "boholderi@nordicmotorhome.dk", 2200, "København N", "Lygten 37");

INSERT INTO accessories
(id, name, description, price)
VALUES
(DEFAULT, "Cykelstativ", "Dette er et cykelstativ hvor du kan have dine cykler. Der er plads til 4 cykler", 200),
(DEFAULT, "Sengetøj", "Ekstra sengetøj i lækker kvalitet. Oplagt til gæster.", 50),
(DEFAULT, "Barnesæde", "Til de små der gerne vil følge med i verden mens far eller mor kører bil.", 50),
(DEFAULT, "Picnicbord", "Ønsker i at nyde frokosten i naturen eller bare on-the-go er et picnicbord oplagt.", 100),
(DEFAULT, "Klapstol", "Nem stol der kan bruges til alle formål.", 50);

INSERT INTO customers
(id, first_name, last_name, phone, mail, zip_code, city, address)
VALUES
(DEFAULT, "John", "Larsen", 12345678, "larsen37@mail.dk", 2800, "Kongens Lyngby", "Kastanievej 5"),
(DEFAULT, "John", "Smith", 12345678, "johnsmith@mail.dk", 2800, "Kongens Lyngby", "Jernbanevej 3A"),
(DEFAULT, "Karen", "Bibo", 12345678, "mail@mail.dk", 2800, "Kongens Lyngby", "Toftebæksvej 9"),
(DEFAULT, "Marie", "Nissen", 12345678, "missen@mail.dk", 2300, "København S", "Gladiolusvej 4"),
(DEFAULT, "Malene", "Larsen", 12345678, "m.larsen@mail.dk", 2800, "Kongens Lyngby", "Kastanievej 2"),
(DEFAULT, "Morten", "Ankerstjerne", 12345678, "anker@mail.dk", 2300, "København S", "Thorshavnsgade 18A"),
(DEFAULT, "Micklas", "Nillekestøv", 12345678, "m.nille.støv@mail.dk", 2300, "København S", "Boltonvej 18"),
(DEFAULT, "Anders", "Bæk", 12345678, "andersbaek@mail.dk", 4100, "Ringsted", "Fluebæksvej 27");

INSERT INTO autocamper_types
(brand, model, beds, seats, description)
VALUES
("Fiat", "Ducato 35", 2, 4, "Der er en fin siddegruppe midt i vognen, hvor 2 personer kan være fastspændt under kørsel. Siddegruppen kan redes op til sengeplads.
Autocamperen er godkendt til 4 personer under transport. Der er en dobbeltseng og mulighed for opredning til 2 børn."),
("Fiat", "Ducato 30", 1, 2, "Siddepladser til chauffør samt 1 ved siden af. Autocamperen er godkendt til 2 personer under transport. Der er en dobbeltseng og ikke mulighed for ekstra opredning."),
("Fiat", "Weinsberg", 4, 4, "Ren luksus autocamper. Der er en fin siddegruppe midt i vognen, hvor 2 personer kan være fastspændt under kørsel. Siddegruppen kan redes op til sengeplads.
Autocamperen er godkendt til 4 personer under transport. Der er en dobbeltseng og mulighed for opredning til 2 børn."),
("Knaus", "Van TI", 2, 4, "Knaus Van TI er ok, men ikke ligeså god som Van TI Plus!"),
("Knaus", "Van TI Plus", 4, 4, "Knaus Van TI Plus er 10 gange bedre end Van TI");

INSERT INTO autocampers
(id, autocamper_type_brand, autocamper_type_model, year, price_day)
VALUES
(DEFAULT, "Fiat", "Ducato 35", 2011, 2500.00),
(DEFAULT, "Fiat", "Ducato 35", 2011, 2500.00),
(DEFAULT, "Fiat", "Ducato 35", 2011, 2500.00),
(DEFAULT, "Fiat", "Ducato 35", 2011, 12.00),
(DEFAULT, "Fiat", "Ducato 35", 2015, 3000.00),
(DEFAULT, "Fiat", "Ducato 35", 2015, 3000.00),
(DEFAULT, "Fiat", "Ducato 35", 2015, 3000.00),
(DEFAULT, "Fiat", "Ducato 35", 2016, 3200.00),
(DEFAULT, "Fiat", "Ducato 35", 2017, 3500.00),
(DEFAULT, "Fiat", "Ducato 35", 2017, 3500.00),
(DEFAULT, "Fiat", "Ducato 35", 2017, 3500.00),
(DEFAULT, "Fiat", "Ducato 35", 2017, 3500.00),
(DEFAULT, "Fiat", "Ducato 30", 2005, 1500.00),
(DEFAULT, "Fiat", "Ducato 30", 2005, 1500.00),
(DEFAULT, "Fiat", "Ducato 30", 2005, 1500.00),
(DEFAULT, "Fiat", "Ducato 30", 2015, 2000.00),
(DEFAULT, "Fiat", "Ducato 30", 2015, 2000.00),
(DEFAULT, "Fiat", "Ducato 30", 2019, 2500.00),
(DEFAULT, "Fiat", "Weinsberg", 2019, 4000.00),
(DEFAULT, "Fiat", "Weinsberg", 2019, 4000.00),
(DEFAULT, "Fiat", "Weinsberg", 2019, 4000.00),
(DEFAULT, "Fiat", "Weinsberg", 2019, 4000.00),
(DEFAULT, "Knaus", "Van TI", 2019, 4300.00),
(DEFAULT, "Knaus", "Van TI", 2019, 4300.00),
(DEFAULT, "Knaus", "Van TI", 2019, 4300.00),
(DEFAULT, "Knaus", "Van TI", 2020, 4500.00),
(DEFAULT, "Knaus", "Van TI", 2020, 4500.00),
(DEFAULT, "Knaus", "Van TI", 2020, 4500.00),
(DEFAULT, "Knaus", "Van TI", 2020, 4500.00),
(DEFAULT, "Knaus", "Van TI Plus", 2020, 5000.00),
(DEFAULT, "Knaus", "Van TI Plus", 2020, 5000.00),
(DEFAULT, "Knaus", "Van TI Plus", 2020, 5000.00);

INSERT INTO bookings
(id, autocamper_id, customer_id, period_start, period_end, dropoff, pickup, price_total)
VALUES
(DEFAULT, 4, 1, "2020-05-05", "2020-05-15", "Kastrup Lufthavn", "Kastrup Lufthavn", 5000),
(DEFAULT, 4, 1, "2020-05-20", "2020-05-30", "Kastrup Lufthavn", "Kastrup Lufthavn", 5000),
(DEFAULT, 5, 1, "2020-05-11", "2020-05-30", "Kastrup Lufthavn", "Kastrup Lufthavn", 5000),
(DEFAULT, 8, 1, "2020-05-18", "2020-05-30", "Kastrup Lufthavn", "Kastrup Lufthavn", 5000);