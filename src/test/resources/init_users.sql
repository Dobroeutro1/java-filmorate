MERGE INTO USERS(id, name, email, login, birthday) VALUES (1, 'Artem', 'artem@email.com', 'artem', '1998-01-06');
MERGE INTO USERS(id, name, email, login, birthday) VALUES (2, 'Andrey', 'andrey@email.com', 'andrey', '2000-01-10');
MERGE INTO USERS(id, name, email, login, birthday) VALUES (3, 'Sasha', 'sasha@email.com', 'sasha', '1995-05-01');

ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 4;
