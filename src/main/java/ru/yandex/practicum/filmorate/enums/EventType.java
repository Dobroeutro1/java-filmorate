package ru.yandex.practicum.filmorate.enums;

public enum EventType {
    FRIEND,
    REVIEW,
    LIKE;

    @Override
    public String toString() {
        return this.name();
    }
}
