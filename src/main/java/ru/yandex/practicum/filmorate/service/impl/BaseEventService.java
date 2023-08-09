package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.EventRepository;
import ru.yandex.practicum.filmorate.enums.EventType;
import ru.yandex.practicum.filmorate.enums.OperationType;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.service.EventService;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseEventService implements EventService {

    private final EventRepository eventRepository;

    @Override
    public void saveFeed(long id, long entityId, EventType eventType, OperationType operation) {
        Event event = Event.builder()
                .userId(id)
                .entityId(entityId)
                .eventType(eventType.toString())
                .operation(operation.toString())
                .timestamp(Instant.now().toEpochMilli()).build();

        eventRepository.saveFeed(event);
        log.info("Event saved: User #{} {} {} #{}.", id, operation.toString().toLowerCase(),
                eventType.toString().toLowerCase(), entityId);
    }

    @Override
    public List<Event> getNewsFeed(long userId) {
        List<Event> events = eventRepository.getNewsFeed(userId);
        log.info("Loading {} events.", events.size());
        return events;
    }

}
