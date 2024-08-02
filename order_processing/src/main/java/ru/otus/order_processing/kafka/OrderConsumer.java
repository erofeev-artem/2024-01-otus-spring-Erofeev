package ru.otus.order_processing.kafka;

import ru.otus.order_processing.dto.OrderDto;

import java.util.List;

public interface OrderConsumer {

    void accept(List<OrderDto> value);
}
