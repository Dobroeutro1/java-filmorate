package ru.yandex.practicum.filmorate.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Review;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Review(rs.getLong("ID"),
                rs.getString("CONTENT"),
                rs.getBoolean("IS_POSITIVE"),
                rs.getLong("USER_ID"),
                rs.getLong("FILM_ID"),
                rs.getInt("USEFUL")
        );
    }
}
