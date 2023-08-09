package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@Builder
public class Event {
    private long eventId;
    private long timestamp;
    private long userId;
    private String eventType;
    private String operation;
    private long entityId;

}
