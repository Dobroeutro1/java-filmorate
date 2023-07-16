MERGE INTO FILMS(id, name, description, duration, release_date, mpa_id) VALUES (1, 'Фильм 1', 'Описание 1', 1600, '2020-01-01', 1);
MERGE INTO FILMS(id, name, description, duration, release_date, mpa_id) VALUES (2, 'Фильм 2', 'Описание 2', 2600, '2020-02-02', 2);
MERGE INTO FILMS(id, name, description, duration, release_date, mpa_id) VALUES (3, 'Фильм 3', 'Описание 3', 3600, '2020-03-03', 3);
MERGE INTO FILMS(id, name, description, duration, release_date, mpa_id) VALUES (4, 'Фильм 4', 'Описание 4', 4600, '2020-04-04', 4);

ALTER TABLE FILMS ALTER COLUMN ID RESTART WITH 5;
