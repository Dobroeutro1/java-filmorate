MERGE INTO MPA (id, name) VALUES (1, 'G');
MERGE INTO MPA (id, name) VALUES (2, 'PG');
MERGE INTO MPA (id, name) VALUES (3, 'PG-13');
MERGE INTO MPA (id, name) VALUES (4, 'R');
MERGE INTO MPA (id, name) VALUES (5, 'NC-17');

MERGE INTO GENRES (id, name) VALUES (1, 'Комедия');
MERGE INTO GENRES (id, name) VALUES (2, 'Драма');
MERGE INTO GENRES (id, name) VALUES (3, 'Мультфильм');
MERGE INTO GENRES (id, name) VALUES (4, 'Триллер');
MERGE INTO GENRES (id, name) VALUES (5, 'Документальный');
MERGE INTO GENRES (id, name) VALUES (6, 'Боевик');

-- MERGE INTO USERS(id, name, email, login, birthday) VALUES (1, 'Artem', 'artem@email.com', 'artem', '1998-01-06');
-- MERGE INTO USERS(id, name, email, login, birthday) VALUES (2, 'Andrey', 'andrey@email.com', 'andrey', '2000-01-10');
-- MERGE INTO USERS(id, name, email, login, birthday) VALUES (3, 'Sasha', 'sasha@email.com', 'sasha', '1995-05-01');

-- MERGE INTO FILMS(id, name, description, duration, release_date, mpa_id)
--     VALUES (1, 'Фильм 1', 'Описание 1', 1600, '2020-01-01', 1);
-- MERGE INTO FILMS(id, name, description, duration, release_date, mpa_id)
--     VALUES (2, 'Фильм 2', 'Описание 2', 2600, '2020-02-02', 2);
-- MERGE INTO FILMS(id, name, description, duration, release_date, mpa_id)
--     VALUES (3, 'Фильм 3', 'Описание 3', 3600, '2020-03-03', 3);
-- MERGE INTO FILMS(id, name, description, duration, release_date, mpa_id)
--     VALUES (4, 'Фильм 4', 'Описание 4', 4600, '2020-04-04', 4);

-- ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 4;
-- ALTER TABLE FILMS ALTER COLUMN ID RESTART WITH 5;

ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE FILMS ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE MPA ALTER COLUMN ID RESTART WITH 6;
ALTER TABLE GENRES ALTER COLUMN ID RESTART WITH 7;
