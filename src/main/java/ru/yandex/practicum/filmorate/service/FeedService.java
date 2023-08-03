package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dao.FeedRepository;
import ru.yandex.practicum.filmorate.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.enums.EventType;
import ru.yandex.practicum.filmorate.enums.OperationType;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Feed;

import java.util.List;

//@Service
//@Slf4j
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@Service
public class FeedService {

//    private final FeedRepository feedRepository;
//    private final UserRepository userRepository;
//
//    public List<Feed> getFeed(long id) {
//        userRepository.getUser(id).orElseThrow(()-> {
//            log.info("Пользователь не существует");
//            throw new NotFoundException("Пользователь не существует");
//        });
//        log.info("Возвращаем ленту событий пользователя с ид {}", id);
//        return feedRepository.getById(id);
//    }
private final FeedRepository feedRepository;

    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
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
