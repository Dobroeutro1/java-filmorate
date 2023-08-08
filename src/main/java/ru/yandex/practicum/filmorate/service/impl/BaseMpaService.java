package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MpaRepository;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseMpaService implements MpaService {

    private final MpaRepository mpaRepository;

    @Override
    public List<MPA> getAll() {
        return mpaRepository.getAll();
    }

    @Override
    public MPA findById(long id) {
        return mpaRepository.findById(id).orElseThrow(() -> {
            log.info(String.format("Ошибка получения MPA с id: %s. MPA не найден", id));
            return new NotFoundException(String.format("MPA с id %s не найден", id));
        });
    }

}
