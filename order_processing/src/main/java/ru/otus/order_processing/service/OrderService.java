package ru.otus.order_processing.service;


import ru.otus.order_processing.dto.OrderDto;

public interface OrderService {

    void save(OrderDto orderDto);

}
