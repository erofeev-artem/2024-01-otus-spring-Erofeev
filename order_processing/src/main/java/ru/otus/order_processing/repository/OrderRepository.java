package ru.otus.order_processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order_processing.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
