package ru.otus.order_processing.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order_processing.model.Customer;
import ru.otus.order_processing.model.Order;
import ru.otus.order_processing.model.OrderStatus;
import ru.otus.order_processing.model.Tariff;
import ru.otus.order_processing.model.ConnectionAddress;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    List<Order> findByUserId(long userId);

    Optional<Order> findByTariffAndCustomerAndConnectionAddressAndOrderStatus(Tariff tariff, Customer customer,
                                                                              ConnectionAddress connectionAddress,
                                                                              OrderStatus status);
}
