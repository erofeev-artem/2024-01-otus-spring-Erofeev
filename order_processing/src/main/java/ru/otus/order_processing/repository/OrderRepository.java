package ru.otus.order_processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order_processing.model.Order;
import ru.otus.order_processing.model.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderStatus(OrderStatus orderStatus);
}
