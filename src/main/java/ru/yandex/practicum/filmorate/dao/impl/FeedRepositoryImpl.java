package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FeedRepository;
import ru.yandex.practicum.filmorate.dao.mapper.FeedRowMapper;
import ru.yandex.practicum.filmorate.model.Feed;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository("FeedRepository")
@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public void saveFeed(Feed feed) {
        String sqlQuery = "INSERT INTO EVENTS (user_id, entity_id, event_type, operation_type, time_stamp) " +
                "VALUES (:userId, :entityId, :eventType, :operation, :timestamp)";

        sqlUpdate(sqlQuery, feed);
    }

    @Override
    public List<Feed> getNewsFeed(long userId) {
        String sql = "SELECT E.* " +
                "FROM EVENTS AS E " +
                "WHERE user_id = :userId " +
                "ORDER BY event_id";

        return jdbcOperations.query(sql, Map.of("userId", userId), new FeedRowMapper());
    }

    private long sqlUpdate(String sqlQuery, Feed feed) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("userId", feed.getUserId());
        map.addValue("entityId", feed.getEntityId());
        map.addValue("operation", feed.getOperation());
        map.addValue("eventType", feed.getEventType());
        map.addValue("timestamp", Instant.ofEpochMilli(feed.getTimestamp()));

        jdbcOperations.update(sqlQuery, map, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
