package ru.yandex.practicum.filmorate.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Feed;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedRowMapper implements RowMapper<Feed> {

    @Override
    public Feed mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Feed.builder()
                .timestamp(rs.getTimestamp("time_stamp").toInstant().toEpochMilli())
                .userId(rs.getLong("user_id"))
                .eventType(rs.getString("event_type"))
                .operation(rs.getString("operation_type"))
                .eventId(rs.getLong("event_id"))
                .entityId(rs.getLong("entity_id"))
                .build();
    }
}
