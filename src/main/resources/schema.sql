DROP TABLE USERS CASCADE;
DROP TABLE FRIENDS CASCADE;
DROP TABLE MPA CASCADE;
DROP TABLE GENRES CASCADE;
DROP TABLE FILMS CASCADE;
DROP TABLE FILM_LIKES CASCADE;
DROP TABLE FILM_GENRES CASCADE;
DROP TABLE REVIEWS CASCADE;
DROP TABLE REVIEWS_LIKES CASCADE;
DROP TABLE DIRECTORS CASCADE;
DROP TABLE FILM_DIRECTORS CASCADE;
DROP TABLE EVENTS CASCADE;

CREATE TABLE IF NOT EXISTS USERS
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(70) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    login       VARCHAR(70) NOT NULL,
    birthday    DATE,
    CONSTRAINT USER_PK PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS FRIENDS
(
    user_id     INT REFERENCES users (id) ON DELETE CASCADE,
    friend_id   INT REFERENCES users (id) ON DELETE CASCADE,
    is_approve  BOOLEAN NOT NULL,
    CONSTRAINT UC_FRIEND UNIQUE (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS MPA
(
    id    INT PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(6),
    CONSTRAINT MPA_PK PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS GENRES
(
    id    INT PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(25),
    CONSTRAINT GENRE_PK PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS FILMS
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(100) NOT NULL,
    description     VARCHAR(255) NOT NULL,
    duration        INT NOT NULL,
    release_date    DATE,
    mpa_id          INT REFERENCES mpa(id) NOT NULL,
    CONSTRAINT FILM_PK PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS FILM_LIKES
(
    user_id     INT REFERENCES users(id) ON DELETE CASCADE,
    film_id     INT REFERENCES films(id) ON DELETE CASCADE,
    CONSTRAINT FILM_LIKE_PK PRIMARY KEY (user_id, film_id)
);

CREATE TABLE IF NOT EXISTS FILM_GENRES
(
    film_id     INT REFERENCES films(id) ON DELETE CASCADE,
    genre_id    INT REFERENCES genres(id) NOT NULL,
    CONSTRAINT FILM_GENRE_PK PRIMARY KEY (film_id, director_id)
);

CREATE TABLE IF NOT EXISTS REVIEWS
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    content         VARCHAR(255) NOT NULL,
    is_positive     BOOLEAN NOT NULL,
    user_id         INT REFERENCES users(id) ON DELETE CASCADE NOT NULL,
    film_id         INT REFERENCES films(id) ON DELETE CASCADE NOT NULL,
    useful          INT NOT NULL
);

CREATE TABLE IF NOT EXISTS REVIEWS_LIKES
(
    review_id       INT REFERENCES reviews(id) ON DELETE CASCADE NOT NULL,
    is_like         BOOLEAN NOT NULL,
    user_id         INT REFERENCES users(id) ON DELETE CASCADE NOT NULL,
    CONSTRAINT REVIEW_USER_PK PRIMARY KEY (review_id, user_id)
);

CREATE TABLE IF NOT EXISTS DIRECTORS
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(70) NOT NULL
);

CREATE TABLE IF NOT EXISTS FILM_DIRECTORS
(
    film_id     INT REFERENCES films(id) ON DELETE CASCADE NOT NULL,
    director_id INT REFERENCES directors(id) ON DELETE CASCADE NOT NULL,
    CONSTRAINT FILM_DIRECTOR_PK PRIMARY KEY (film_id, director_id)
);

CREATE TABLE IF NOT EXISTS EVENTS (
    event_id        INT generated by default as identity primary key,
    user_id         INT NOT NULL,
    time_stamp      TIMESTAMP NOT NULL,
    event_type      VARCHAR(50) NOT NULL,
    operation_type  VARCHAR(50) NOT NULL,
    entity_id       INT NOT NULL
);

