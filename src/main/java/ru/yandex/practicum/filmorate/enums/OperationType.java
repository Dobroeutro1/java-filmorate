package ru.yandex.practicum.filmorate.enums;

public enum OperationType {
    REMOVE,
    ADD,
    UPDATE;

    @Override
    public String toString() {
        return this.name();
    }
}
