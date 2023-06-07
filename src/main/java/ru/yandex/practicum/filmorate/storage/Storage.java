package ru.yandex.practicum.filmorate.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Storage<T> {

    protected long lastId = 0;
    protected final HashMap<Long, T> items = new HashMap<>();

    public List<T> getAll() {
        return new ArrayList<>(items.values());
    }

}
