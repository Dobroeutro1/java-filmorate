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

ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE FILMS ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE MPA ALTER COLUMN ID RESTART WITH 6;
ALTER TABLE GENRES ALTER COLUMN ID RESTART WITH 7;
