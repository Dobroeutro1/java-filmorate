package ru.yandex.practicum.filmorate.dao;

import java.util.List;

public interface Repository<T> {

    List<T> getAll();

    T create(T item);

    T update(T item);

}
