package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface MpaService {

    List<MPA> getAll();

    MPA findById(long id);

}
