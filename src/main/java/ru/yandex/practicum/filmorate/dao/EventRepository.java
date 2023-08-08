package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Event;

import java.util.List;

public interface EventRepository {

    List<Event> getNewsFeed(long userId);

    void saveFeed(Event event);
}
