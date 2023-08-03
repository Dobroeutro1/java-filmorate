package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.enums.EventType;
import ru.yandex.practicum.filmorate.enums.OperationType;
import ru.yandex.practicum.filmorate.model.Feed;

import java.util.List;

public interface FeedRepository {

//    void addFeed(long entityId, long userID, long timeStamp, EventType eventType, OperationType operation);
//
//    List<Feed> getById(long id);
List<Feed> getNewsFeed(long userId);

    void saveFeed(long id, long entityId, EventType eventType, OperationType operation);
}
