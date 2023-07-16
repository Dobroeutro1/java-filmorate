package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.UserFriendsRepository;
import ru.yandex.practicum.filmorate.dao.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Repository
public class UserFriendsRepositoryImpl implements UserFriendsRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public UserFriendsRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<User> getUserFriends(User user) {
        final String sqlQuery = "SELECT U.* " +
                "FROM USERS U " +
                "WHERE U.ID IN (SELECT FRIEND_ID " +
                                "FROM FRIENDS " +
                                "WHERE USER_ID = :user_id AND IS_APPROVE = TRUE)";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("user_id", user.getId());

        return jdbcOperations.query(sqlQuery, map, new UserRowMapper());
    }

    public List<User> getCommonFriends(long fUserId, long sUserId) {
        final String sqlQuery = "SELECT * " +
                "FROM USERS " +
                "WHERE id IN (SELECT DISTINCT user_id " +
                                "FROM FRIENDS " +
                                "WHERE friend_id = :f_user_id " +
                                "OR friend_id = :s_user_id) " +
                                "AND id NOT IN (:f_user_id, :s_user_id)";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("f_user_id", fUserId);
        map.addValue("s_user_id", sUserId);

        return jdbcOperations.query(sqlQuery, map, new UserRowMapper());
    }

    @Override
    public void add(long userId, long friendId) {
        final String sqlQuery = "INSERT INTO FRIENDS(USER_ID, FRIEND_ID, IS_APPROVE) " +
                "VALUES (:user_id, :friend_id, true), (:friend_id, :user_id, false)";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("user_id", userId);
        map.addValue("friend_id", friendId);

        jdbcOperations.update(sqlQuery, map);
    }

    @Override
    public void approve(long userId, long friendId) {
        final String sqlQuery = "UPDATE FRIENDS SET IS_APPROVE = true WHERE USER_ID = :user_id AND FRIEND_ID = :friend_id";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("user_id", userId);
        map.addValue("friend_id", friendId);

        jdbcOperations.update(sqlQuery, map);
    }

    @Override
    public void remove(long userId, long friendId) {
        final String sqlQuery = "DELETE FROM FRIENDS " +
                "WHERE (FRIEND_ID = :user_id AND USER_ID = :friend_id) OR (FRIEND_ID = :friend_id AND USER_ID = :user_id)";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("user_id", userId);
        map.addValue("friend_id", friendId);

        jdbcOperations.update(sqlQuery, map);
    }

}
