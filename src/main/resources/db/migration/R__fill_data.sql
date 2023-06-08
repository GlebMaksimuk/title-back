TRUNCATE TABLE director_title CASCADE;
TRUNCATE TABLE actor_title CASCADE;
TRUNCATE TABLE title CASCADE;
TRUNCATE TABLE director CASCADE;
TRUNCATE TABLE actor CASCADE;
TRUNCATE TABLE member CASCADE;

INSERT INTO director (director_id, name, surname, birth_date)
VALUES (1, 'John', 'Smith', '1960-07-25'),
       (2, 'Steven', 'Spielberg', '1976-01-06'),
       (3, 'Quentin', 'Tarantino', '1963-03-27');

INSERT INTO actor (actor_id, name, surname, birth_date)
VALUES (1, 'John', 'Smith', '1960-07-25'),
       (2, 'Bruce', 'Willis', '1955-03-19');

INSERT INTO title(title_id, name, budget, box_office, premiere_date, runtime)
VALUES (1, 'Bridge to Therabithia', 50, 100, '2005-03-16', '130'),
       (2, 'Saving Private Ryan', 200, 700, '1998-07-24', '168'),
       (3, 'Kill Bill Vol. 1', 100, 500, '2001-06-11', '144'),
       (4, 'Kill Bill Vol. 2', 200, 700, '2003-01-24', '155');

INSERT INTO director_title(director_id, title_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (3, 4),
       (3, 2);

INSERT INTO actor_title(actor_id, title_id)
VALUES (1, 1),
       (2, 2),
       (2, 3);

INSERT INTO member(login, password)
VALUES ('admin', 'admin'),
       ('test', 'test')
