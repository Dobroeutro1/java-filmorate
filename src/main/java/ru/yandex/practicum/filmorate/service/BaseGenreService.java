package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreRepository;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseGenreService implements GenreService {

    private final GenreRepository genresRepository;

    @Override
    public List<Genre> getAll() {
        return genresRepository.getAll();
    }

    @Override
    public Genre findById(long id) {
        return genresRepository.findById(id).orElseThrow(() -> {
            log.info(String.format("Ошибка получения жанра с id: %s. Жанр не найден", id));
            return new NotFoundException(String.format("Жанр с id %s не найден", id));
        });
    }

}
