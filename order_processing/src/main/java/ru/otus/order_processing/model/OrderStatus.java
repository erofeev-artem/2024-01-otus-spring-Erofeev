package ru.otus.order_processing.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.otus.order_processing.jackson.StatusDeserializer;

@Getter
@RequiredArgsConstructor
@JsonDeserialize(using = StatusDeserializer.class)
public enum OrderStatus {
    NEW("Новый"),
    CONFIRMED("Подтвержден"),
    COMPLETED("Выполнен"),
    REJECTED("Отклонен");

    private final String status;
}
