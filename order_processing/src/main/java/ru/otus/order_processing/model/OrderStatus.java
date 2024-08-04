package ru.otus.order_processing.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.otus.order_processing.jackson.StatusDeserializer;
import ru.otus.order_processing.jackson.StatusSerializer;

@Getter
@RequiredArgsConstructor
@JsonSerialize(using = StatusSerializer.class)
@JsonDeserialize(using = StatusDeserializer.class)
public enum OrderStatus {
    NEW("Новый"),
    CONFIRMED("Подтвержден"),
    COMPLETED("Выполнен"),
    REJECTED("Отклонен");

    private final String status;
}
