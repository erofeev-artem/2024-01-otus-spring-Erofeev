package ru.otus.order_processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order_processing.model.*;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    List<Order> findByEngineerId(long engineerId);

    Optional<Order> findByTariffAndCustomerAndConnectionAddressAndOrderStatus(Tariff tariff, Customer customer,
                                                                              ConnectionAddress connectionAddress,
                                                                              OrderStatus status);
}
