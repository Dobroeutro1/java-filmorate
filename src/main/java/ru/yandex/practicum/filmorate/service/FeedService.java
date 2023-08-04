package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dao.FeedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserRepository;
import ru.yandex.practicum.filmorate.enums.EventType;
import ru.yandex.practicum.filmorate.enums.OperationType;
import ru.yandex.practicum.filmorate.model.Feed;

import java.util.List;

@Slf4j
@Service
public class FeedService {

private final FeedRepository feedRepository;
private final UserRepository userService;

    public FeedService(FeedRepository feedRepository, UserRepository userService) {
        this.feedRepository = feedRepository;
        this.userService = userService;
    }


    public void saveFeed(long id, long entityId, EventType eventType, OperationType operation){
        feedRepository.saveFeed(id, entityId, eventType, operation);
        log.debug("Event saved: User #{} {} {} #{}.",id,operation.toString().toLowerCase(),
                eventType.toString().toLowerCase(), entityId );
    }

    public List<Feed> getNewsFeed(long userId) {
        List<Feed> feeds = feedRepository.getNewsFeed(userId);
        log.debug("Loading {} events.", feeds.size());
        return feeds;
    }
}
