package ru.otus.order_processing.service;


import ru.otus.order_processing.dto.OrderDto;
import ru.otus.order_processing.model.Order;
import ru.otus.order_processing.model.OrderStatus;

import java.util.List;

public interface OrderService {

    void save(OrderDto orderDto);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

}
