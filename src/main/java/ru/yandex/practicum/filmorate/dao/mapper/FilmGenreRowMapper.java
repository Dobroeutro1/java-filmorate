package ru.yandex.practicum.filmorate.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class FilmGenreRowMapper implements RowMapper<HashMap<Long, Genre>> {
    @Override
    public HashMap<Long, Genre> mapRow(ResultSet rs, int rowNum) throws SQLException {
        HashMap<Long, Genre> result = new HashMap<>();

        result.put(rs.getLong("FILM_ID"), new Genre(rs.getLong("ID"),
                rs.getString("NAME")));

        return result;
    }
}
