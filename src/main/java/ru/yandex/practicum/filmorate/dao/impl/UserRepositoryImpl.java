package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.UserRepository;
import ru.yandex.practicum.filmorate.dao.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public UserRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<User> getAll() {
        final String sqlQuery = "SELECT * FROM USERS";
        return jdbcOperations.query(sqlQuery, new UserRowMapper());
    }

    @Override
    public Optional<User> getUser(long userId) {
        final String sqlQuery = "SELECT * FROM USERS WHERE ID = :userId";
        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("userId", userId);

        List<User> users = jdbcOperations.query(sqlQuery, map, new UserRowMapper());

        if (users.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(users.get(0));
    }

    @Override
    public User create(User user) {
        try {
            if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }

            log.info("Создание пользователя: " + user);

            String sqlQuery = "INSERT INTO USERS (NAME, EMAIL, LOGIN, BIRTHDAY) values (:name, :email, :login, :birthday)";

            user.setId(sqlUpdate(sqlQuery, user));

            return user;
        } catch (Exception e) {
            log.error("ERROR: " + e);

            throw new RuntimeException(e);
        }
    }

    @Override
    public User update(User user) {
        try {
            log.info("Изменение пользователя: " + user);

            final String sqlQuery = "UPDATE USERS SET NAME = :name, EMAIL = :email, LOGIN = :login, BIRTHDAY = :birthday "
                    + "WHERE ID = :userId";

            sqlUpdate(sqlQuery, user);

            return user;
        } catch (Exception e) {
            log.error("ERROR: " + e);

            throw new RuntimeException(e);
        }
    }

    private long sqlUpdate(String sqlQuery, User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("name", user.getName());
        map.addValue("email", user.getEmail());
        map.addValue("login", user.getLogin());
        map.addValue("birthday", user.getBirthday());
        map.addValue("userId", user.getId());

        jdbcOperations.update(sqlQuery, map, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

}
