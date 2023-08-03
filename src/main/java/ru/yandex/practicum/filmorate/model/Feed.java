package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.filmorate.enums.EventType;
import ru.yandex.practicum.filmorate.enums.OperationType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class Feed {
//    @NotNull
//    @NotEmpty
    private long eventId;
//    @NotNull
//    @NotEmpty
    private long entityId;
//    @NotNull
//    @NotEmpty
    private long timestamp;
//    @NotNull
//    @NotEmpty
    private long userId;
//    @NotNull
//    @NotEmpty
//    private EventType eventType;
//    @NotNull
//    @NotEmpty
//    private OperationType operation;
private String eventType;

    private String operation;

}
