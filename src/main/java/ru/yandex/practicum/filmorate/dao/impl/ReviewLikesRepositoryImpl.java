package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.ReviewLikeRepository;
import ru.yandex.practicum.filmorate.dao.mapper.ReviewLikeRowMapper;
import ru.yandex.practicum.filmorate.model.ReviewLike;

import java.util.List;

@Slf4j
@Repository
public class ReviewLikesRepositoryImpl implements ReviewLikeRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public ReviewLikesRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Boolean isHaveUserLike(Long reviewId, Long userId) {
        final String sqlQuery = "SELECT * FROM REVIEWS_LIKES WHERE REVIEW_ID = :review_id AND USER_ID = :user_id";
        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("review_id", reviewId);
        map.addValue("user_id", userId);

        List<ReviewLike> reviewLikes = jdbcOperations.query(sqlQuery, map, new ReviewLikeRowMapper());

        return !reviewLikes.isEmpty();
    }

    @Override
    public void add(Long reviewId, Long userId, Boolean isLike) {
        final String sqlQuery = "INSERT INTO REVIEWS_LIKES (REVIEW_ID, USER_ID, IS_LIKE) " +
                "VALUES (:review_id, :user_id, :is_like)";
        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("review_id", reviewId);
        map.addValue("user_id", userId);
        map.addValue("is_like", isLike);

        jdbcOperations.update(sqlQuery, map);
    }

    @Override
    public void remove(Long reviewId, Long userId) {
        final String sqlQuery = "DELETE FROM REVIEWS_LIKES WHERE REVIEW_ID = :review_id AND USER_ID = :user_id";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("review_id", reviewId);
        map.addValue("user_id", userId);

        jdbcOperations.update(sqlQuery, map);
    }

}
