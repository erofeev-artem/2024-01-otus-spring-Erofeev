package ru.otus.order.kafka;

import ru.otus.order.dto.OrderDto;

public interface DataProducer {
    void send(OrderDto value);
}
