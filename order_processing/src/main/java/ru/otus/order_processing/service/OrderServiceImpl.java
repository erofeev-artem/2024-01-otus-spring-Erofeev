package ru.otus.order_processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.order_processing.dto.EngineerOrderUpdateDto;
import ru.otus.order_processing.dto.OrderDto;
import ru.otus.order_processing.exception.EntityNotFoundException;
import ru.otus.order_processing.mapper.OrderMapper;
import ru.otus.order_processing.model.Order;
import ru.otus.order_processing.model.OrderStatus;
import ru.otus.order_processing.model.Tariff;
import ru.otus.order_processing.model.Customer;
import ru.otus.order_processing.model.ConnectionAddress;
import ru.otus.order_processing.repository.OrderRepository;
import ru.otus.order_processing.model.Client;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final CustomerService customerService;

    private final ConnectionAddressService connectionAddressService;

    private final TariffService tariffService;

    private final ClientService clientService;

    @Override
    public OrderDto saveOrder(OrderDto orderDto) {
        Order order;
        if (orderDto.orderStatus().equals(OrderStatus.NEW)) {
            order = saveNewOrder(orderDto);
        } else {
            order = save(orderDto);
        }
        return orderMapper.orderToDto(order);
    }

    @Override
    @Transactional
    public OrderDto update(EngineerOrderUpdateDto updateDto) {
        Order order = orderRepository.findById(updateDto.orderId()).orElseThrow(()
                -> new EntityNotFoundException("Order with id number %d not found".formatted(updateDto.orderId())));
        if (updateDto.tariffDto() != null) {
            order.setTariff(tariffService.dtoToEntity(updateDto.tariffDto()));
        }
        if (updateDto.clientDto() != null) {
            Client client = clientService.dtoToEntity(updateDto.clientDto());
            Client savedClient = clientService.save(client);
            order.setClient(savedClient);
        }
        if (updateDto.orderStatus() != null) {
            order.setOrderStatus(updateDto.orderStatus());
        }
        if (updateDto.comment() != null) {
            order.setComment(updateDto.comment());
        }
        Order savedOrder = orderRepository.save(order);
        return orderMapper.orderToDto(savedOrder);
    }

    @Override
    public List<Order> findByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus);
    }

    @Override
    public List<Order> findAssignedOrders(long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional
    private Order saveNewOrder(OrderDto orderDto) {
        ConnectionAddress connectionAddress = connectionAddressService.save(orderDto.connectionAddressDto());
        Customer customer = customerService.saveOrUpdate(orderDto.customerDto());
        Tariff tariff = tariffService
                .findByNameAndArchived(orderDto.tariffDto().name(), orderDto.tariffDto().archived());
        Optional<Order> orderOptional = findByTariffAndCustomerAndConnectionAddressAndStatus(tariff, customer,
                connectionAddress, orderDto.orderStatus());
        if (orderOptional.isEmpty()) {
            Order order = Order.builder()
                    .id(0)
                    .tariff(tariff)
                    .customer(customer)
                    .connectionAddress(connectionAddress)
                    .orderStatus(OrderStatus.NEW)
                    .build();
            return orderRepository.save(order);
        } else {
            return orderOptional.get();
        }
    }

    @Transactional
    private Order save(OrderDto orderDto) {
        Order existedOrder = orderRepository.findById(orderDto.id()).orElseThrow(()
                -> new EntityNotFoundException("Order with id number %d not found".formatted(orderDto.id())));
        Order newOrder = orderMapper.dtoToOrder(orderDto);
        existedOrder.setTariff(newOrder.getTariff());
        existedOrder.setClient(newOrder.getClient());
        existedOrder.setConnectionAddress(newOrder.getConnectionAddress());
        existedOrder.setUser(newOrder.getUser());
        existedOrder.setOrderStatus(newOrder.getOrderStatus());
        existedOrder.setConnectionDate(newOrder.getConnectionDate());
        existedOrder.setComment(orderDto.comment());
        return orderRepository.save(existedOrder);
    }

    private Optional<Order> findByTariffAndCustomerAndConnectionAddressAndStatus(Tariff tariff, Customer customer,
                                                                                 ConnectionAddress connectionAddress,
                                                                                 OrderStatus status) {
        return orderRepository
                .findByTariffAndCustomerAndConnectionAddressAndOrderStatus(tariff, customer, connectionAddress, status);
    }
}
