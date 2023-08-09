package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DirectorRepository;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DirectorService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseDirectorService implements DirectorService {

    private final DirectorRepository directorRepository;

    @Override
    public List<Director> getAll() {
        return directorRepository.getAll();
    }

    @Override
    public Director findById(long directorId) {
        return directorRepository.findById(directorId).orElseThrow(() -> {
            log.info("Ошибка получения Режиссёра с id: {}. Режиссёр не найден", directorId);
            return new NotFoundException(String.format("Режиссёр с id %s. не найден", directorId));
        });
    }

    @Override
    public Director create(Director director) {
        return directorRepository.create(director);
    }

    @Override
    public Director update(Director director) {
        findById(director.getId());
        return directorRepository.update(director);
    }

    @Override
    public Director delete(Long directorId) {
        Director director = findById(directorId);
        directorRepository.delete(directorId);
        return director;
    }

}
