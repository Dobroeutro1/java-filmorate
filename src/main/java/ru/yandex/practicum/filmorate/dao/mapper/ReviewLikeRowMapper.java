package ru.yandex.practicum.filmorate.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.ReviewLike;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewLikeRowMapper implements RowMapper<ReviewLike> {
    @Override
    public ReviewLike mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ReviewLike(rs.getLong("REVIEW_ID"),
                rs.getBoolean("IS_LIKE"),
                rs.getLong("USER_ID")
        );
    }
}
