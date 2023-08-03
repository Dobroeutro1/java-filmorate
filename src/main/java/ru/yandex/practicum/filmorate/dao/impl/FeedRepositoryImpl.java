package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FeedRepository;
import ru.yandex.practicum.filmorate.enums.EventType;
import ru.yandex.practicum.filmorate.enums.OperationType;
import ru.yandex.practicum.filmorate.model.Feed;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

//@Repository
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Repository("FeedRepository")
public class FeedRepositoryImpl implements FeedRepository {

//    private final JdbcTemplate jdbcTemplate;
//
//    @Override
//    public void addFeed(long entityId, long userId, long timeStamp,
//                        EventType eventType, OperationType operation) {
//        String sqlQuery = "INSERT INTO EVENTS (entity_id, user_id, time_stamp, event_type, event_operation) " +
//                "values (?, ?, ?, ?, ?)";
//        jdbcTemplate.update(sqlQuery, entityId, userId, timeStamp, eventType.toString(), operation.toString());
//    }
//
//    @Override
//    public List<Feed> getById(long id) {
//        String sqlGetFeed = "select * from events where user_id = ? order by EVENT_ID";
//        return jdbcTemplate.query(sqlGetFeed, this::makeFeed, id);
//    }
//
//    Feed makeFeed(ResultSet rs, int rowNum) throws SQLException {
//        long eventId = rs.getLong("event_id");
//        long entityId = rs.getLong("entity_id");
//        long userId = rs.getLong("user_id");
//        long time = rs.getLong("time_stamp");
//        EventType eventType = EventType.valueOf(rs.getString("event_type"));
//        OperationType operation = OperationType.valueOf(rs.getString("event_operation"));
//        return new Feed(eventId, entityId, time, userId, eventType, operation);
//    }
private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FeedRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveFeed(long id, long entityId, EventType eventType, OperationType operation) {
        String sqlQuery = "INSERT INTO EVENTS (time_stamp," +
                "user_id," +
                "event_type, " +
                "operation_type," +
                "entity_id) " +
                "VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sqlQuery, Instant.now(), id, eventType.name(), operation.name(), entityId);
    }

    @Override
    public List<Feed> getNewsFeed(long userId) {
        String sql = "SELECT time_stamp," +
                "user_id," +
                "event_type," +
                "operation_type," +
                "event_id," +
                "entity_id " +
                "FROM EVENTS WHERE user_id = ?" +
                "ORDER BY event_id;";
        return jdbcTemplate.query(sql, this::makeFeed, userId);
    }

    private Feed makeFeed(ResultSet resultSet, long rowNum) throws SQLException {
        return Feed.builder()
                .timestamp(resultSet.getTimestamp("time_stamp").toInstant().toEpochMilli())
                .userId(resultSet.getLong("user_id"))
                .eventType(resultSet.getString("event_type"))
                .operation(resultSet.getString("operation_type"))
                .eventId(resultSet.getLong("event_id"))
                .entityId(resultSet.getLong("entity_id"))
                .build();
    }

}
