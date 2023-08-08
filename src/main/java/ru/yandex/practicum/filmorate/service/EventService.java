package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.enums.EventType;
import ru.yandex.practicum.filmorate.enums.OperationType;
import ru.yandex.practicum.filmorate.model.Event;

import java.util.List;

public interface EventService {

    void saveFeed(long id, long entityId, EventType eventType, OperationType operation);

    List<Event> getNewsFeed(long userId);
}
