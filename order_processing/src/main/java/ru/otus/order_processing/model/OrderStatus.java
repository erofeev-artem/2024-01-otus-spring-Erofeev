package ru.otus.order_processing.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    NEW("Новый"),
    CONFIRMED("Подтвержден"),
    COMPLETED("Выполнен"),
    REJECTED("Отклонен");

    private final String status;
}
