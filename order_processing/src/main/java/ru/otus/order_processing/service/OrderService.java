package ru.otus.order_processing.service;


import ru.otus.order_processing.dto.EngineerOrderUpdateDto;
import ru.otus.order_processing.dto.OrderDto;
import ru.otus.order_processing.model.Order;
import ru.otus.order_processing.model.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderDto saveOrder(OrderDto orderDto);

    OrderDto update(EngineerOrderUpdateDto updateDto);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    List<Order> findAssignedOrders(long engineerId);

}
