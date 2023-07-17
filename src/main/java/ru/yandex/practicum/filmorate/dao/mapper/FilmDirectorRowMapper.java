package ru.yandex.practicum.filmorate.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Director;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class FilmDirectorRowMapper implements RowMapper<HashMap<Long, Director>> {

    @Override
    public HashMap<Long, Director> mapRow(ResultSet rs, int rowNum) throws SQLException {
        HashMap<Long, Director> result = new HashMap<>();

        result.put(rs.getLong("FILM_ID"), Director.builder()
                .id(rs.getLong("ID"))
                .name(rs.getString("NAME"))
                .build());

        return result;
    }

}
