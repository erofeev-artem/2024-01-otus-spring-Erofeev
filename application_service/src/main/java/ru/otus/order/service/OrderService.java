package ru.otus.order.service;

import ru.otus.order.dto.OrderDto;

public interface OrderService {
    void send(OrderDto orderDto);
}
