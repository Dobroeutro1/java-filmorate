package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface Storage<T> {

    List<T> getAll();

    T create(T item);

    T update(T item);

}
