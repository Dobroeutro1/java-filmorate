package ru.yandex.practicum.filmorate.enums;

import ru.yandex.practicum.filmorate.exeption.NotFoundException;

public enum EventType {
    FRIEND,
    REVIEW,
    LIKE;

    @Override
    public String toString() {
        return this.name();
    }

    public static EventType toEnum(String s) {
        for (EventType e : EventType.values()) {
            if (e.name().equals(s)) return e;
        }
        throw new NotFoundException("ошибка приведения");
    }
}
