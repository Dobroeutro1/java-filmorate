package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Feed;

import java.util.List;

public interface FeedRepository {

    List<Feed> getNewsFeed(long userId);

    void saveFeed(Feed feed);
}
