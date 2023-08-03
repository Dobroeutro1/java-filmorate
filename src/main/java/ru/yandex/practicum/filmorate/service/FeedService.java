package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dao.FeedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.enums.EventType;
import ru.yandex.practicum.filmorate.enums.OperationType;
import ru.yandex.practicum.filmorate.model.Feed;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
public class FeedService {

    private final FeedRepository feedRepository;

    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }


    public void saveFeed(long id, long entityId, EventType eventType, OperationType operation) {
        Feed feed = Feed.builder()
                .userId(id)
                .entityId(entityId)
                .eventType(eventType.toString())
                .operation(operation.toString())
                .timestamp(Instant.now().toEpochMilli()).build();

        feedRepository.saveFeed(feed);
        log.info("Event saved: User #{} {} {} #{}.",id,operation.toString().toLowerCase(),
                eventType.toString().toLowerCase(), entityId);
    }

    public List<Feed> getNewsFeed(long userId) {
        List<Feed> feeds = feedRepository.getNewsFeed(userId);
        log.info("Loading {} events.", feeds.size());
        return feeds;
    }
}
