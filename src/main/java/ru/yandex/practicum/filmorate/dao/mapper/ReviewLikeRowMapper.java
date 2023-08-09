package ru.yandex.practicum.filmorate.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.ReviewLike;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewLikeRowMapper implements RowMapper<ReviewLike> {
    @Override
    public ReviewLike mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ReviewLike.builder()
                .reviewId(rs.getLong("REVIEW_ID"))
                .isLike(rs.getBoolean("IS_LIKE"))
                .userId(rs.getLong("USER_ID"))
                .build();
    }
}
