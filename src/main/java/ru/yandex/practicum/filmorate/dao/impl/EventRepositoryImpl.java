package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.EventRepository;
import ru.yandex.practicum.filmorate.dao.mapper.EventRowMapper;
import ru.yandex.practicum.filmorate.model.Event;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository("FeedRepository")
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public void saveFeed(Event event) {
        String sqlQuery = "INSERT INTO EVENTS (user_id, entity_id, event_type, operation_type, time_stamp) " +
                "VALUES (:userId, :entityId, :eventType, :operation, :timestamp)";

        sqlUpdate(sqlQuery, event);
    }

    @Override
    public List<Event> getNewsFeed(long userId) {
        String sql = "SELECT E.* " +
                "FROM EVENTS AS E " +
                "WHERE user_id = :userId " +
                "ORDER BY event_id";

        return jdbcOperations.query(sql, Map.of("userId", userId), new EventRowMapper());
    }

    private long sqlUpdate(String sqlQuery, Event event) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("userId", event.getUserId());
        map.addValue("entityId", event.getEntityId());
        map.addValue("operation", event.getOperation());
        map.addValue("eventType", event.getEventType());
        map.addValue("timestamp", Instant.ofEpochMilli(event.getTimestamp()));

        jdbcOperations.update(sqlQuery, map, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
