package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.ReviewRepository;
import ru.yandex.practicum.filmorate.dao.mapper.ReviewRowMapper;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<Review> getAll(Long filmId, Integer count) {
        String sqlQuery = "SELECT * FROM REVIEWS ";

        MapSqlParameterSource map = new MapSqlParameterSource();

        if (filmId != null) {
            sqlQuery += "WHERE film_id = :film_id ";

            map.addValue("film_id", filmId);
        }

        sqlQuery += "ORDER BY USEFUL DESC LIMIT :count";

        map.addValue("count", count);

        return jdbcOperations.query(sqlQuery, map, new ReviewRowMapper());
    }

    @Override
    public Optional<Review> getReview(long reviewId) {
        final String sqlQuery = "SELECT * FROM REVIEWS WHERE ID = :review_id";
        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("review_id", reviewId);

        List<Review> reviews = jdbcOperations.query(sqlQuery, map, new ReviewRowMapper());

        if (reviews.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(reviews.get(0));
    }

    @Override
    public Review create(Review review) {
        log.info("Создание отзыва: " + review);

        String sqlQuery = "INSERT INTO REVIEWS (CONTENT, IS_POSITIVE, USER_ID, FILM_ID, USEFUL) " +
                "VALUES (:content, :is_positive, :user_id, :film_id, :useful)";

        review.setReviewId(sqlUpdate(sqlQuery, review));

        return review;
    }

    @Override
    public Review update(Review review) {
        log.info("Изменение отзыва: " + review);

        final String sqlQuery = "UPDATE REVIEWS SET CONTENT = :content, IS_POSITIVE = :is_positive " +
                "WHERE ID = :review_id";

        sqlUpdate(sqlQuery, review);

        return review;
    }

    @Override
    public void remove(long reviewId) {
        final String sqlQuery = "DELETE FROM REVIEWS WHERE ID = :review_id";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("review_id", reviewId);

        jdbcOperations.update(sqlQuery, map);
        log.info(String.format("Удален отзыв с id %s", reviewId));
    }

    @Override
    public void addRating(long reviewId, long userId) {
        final String sqlQuery = "UPDATE REVIEWS SET USEFUL = USEFUL + 1 WHERE ID = :review_id";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("review_id", reviewId);

        jdbcOperations.update(sqlQuery, map);
        log.info(String.format("Добавлена оценка с id %s от пользователя с id %s", reviewId, userId));
    }

    @Override
    public void removeRating(long reviewId, long userId) {
        final String sqlQuery = "UPDATE REVIEWS SET USEFUL = USEFUL - 1 WHERE ID = :review_id";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("review_id", reviewId);

        jdbcOperations.update(sqlQuery, map);
        log.info(String.format("Убрана оценка отзыву с id %s от пользователя с id %s", reviewId, userId));
    }

    private long sqlUpdate(String sqlQuery, Review review) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("content", review.getContent());
        map.addValue("is_positive", review.getIsPositive());
        map.addValue("user_id", review.getUserId());
        map.addValue("film_id", review.getFilmId());
        map.addValue("useful", review.getUseful());
        map.addValue("review_id", review.getReviewId());

        jdbcOperations.update(sqlQuery, map, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

}
