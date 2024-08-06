package ru.otus.order.kafka;

public interface DataProducer {
    void send(OrderMessage value);
}
