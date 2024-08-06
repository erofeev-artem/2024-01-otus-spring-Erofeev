package ru.otus.order_processing.kafka;

import java.util.List;

public interface OrderConsumer {

    void accept(List<OrderMessage> value);
}
