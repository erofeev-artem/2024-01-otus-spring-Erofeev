package ru.otus.order_processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.order_processing.dto.ConnectionAddressDto;
import ru.otus.order_processing.dto.OrderDto;
import ru.otus.order_processing.exception.EntityNotFoundException;
import ru.otus.order_processing.mapper.ClientMapper;
import ru.otus.order_processing.mapper.ConnectionAddressMapper;
import ru.otus.order_processing.mapper.OrderMapper;
import ru.otus.order_processing.mapper.TariffMapper;
import ru.otus.order_processing.model.*;
import ru.otus.order_processing.repository.ClientRepository;
import ru.otus.order_processing.repository.ConnectionAddressRepository;
import ru.otus.order_processing.repository.OrderRepository;
import ru.otus.order_processing.repository.TariffRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ClientRepository clientRepository;

    private final ConnectionAddressRepository connectionAddressRepository;

    private final TariffRepository tariffRepository;

    private final ClientMapper clientMapper;

    private final ConnectionAddressMapper connectionAddressMapper;
    private final OrderMapper orderMapper;
    private final TariffMapper tariffMapper;
    private final CustomerService customerService;

    @Transactional
    @Override
    public void save(OrderDto orderDto) {
        if (orderDto.orderStatus().equals(OrderStatus.NEW)) {
            saveNewOrder(orderDto);
        } else if (orderDto.orderStatus().equals(OrderStatus.CONFIRMED)) {
            saveConfirmedOrder(orderDto);

        }
//        String tariffName = orderDto.tariffDto().name();
//        Tariff tariff = tariffRepository.findByName(tariffName)
//                .orElseThrow(()
//                        -> new EntityNotFoundException("Tariff with name %s not found".formatted(tariffName)));
//        ConnectionAddressDto connectionAddressDto = orderDto.connectionAddressDto();
//        ConnectionAddress connectionAddress = connectionAddressMapper.dtoToAddress(connectionAddressDto);
//        ConnectionAddress savedConnectionAddress = connectionAddressRepository.save(connectionAddress);
//        CustomerDto customerDto = orderDto.customerDto();
//        Customer savedCustomer;
//        Optional<Client> existingClient = clientRepository.findByFirstNameAndMiddleNameAndLastNameAndPhoneNumber(
//                clientDto.firstName(), clientDto.middleName(), clientDto.lastName(), clientDto.phoneNumber());
//        if (existingClient.isEmpty()) {
//            Client client = clientMapper.dtoToClient(orderDto.clientDto());
//            savedClient = clientRepository.save(client);
//        } else {
//            savedClient = existingClient.get();
//        }
//        Order order = Order.builder()
//                .id(0)
//                .tariff(tariff)
//                .client(savedClient)
//                .connectionAddress(savedConnectionAddress)
//                .orderStatus(OrderStatus.NEW)
//                .build();
//        orderRepository.save(order);
    }

    @Override
    public List<Order> findByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus);
    }

//    public void setEngineer(O){
//        orderRepository.save()

//    }

    private Order saveNewOrder(OrderDto orderDto) {
        String tariffName = orderDto.tariffDto().name();
        Tariff tariff = tariffRepository.findByName(tariffName)
                .orElseThrow(()
                        -> new EntityNotFoundException("Tariff with name %s not found".formatted(tariffName)));
        ConnectionAddressDto connectionAddressDto = orderDto.connectionAddressDto();
        ConnectionAddress connectionAddress = connectionAddressMapper.dtoToAddress(connectionAddressDto);
        ConnectionAddress savedConnectionAddress = connectionAddressRepository.save(connectionAddress);
        Customer savedCustomer = customerService.save(orderDto.customerDto());
        Order order = Order.builder()
                .id(0)
                .tariff(tariff)
                .customer(savedCustomer)
                .connectionAddress(savedConnectionAddress)
                .orderStatus(OrderStatus.NEW)
                .build();
        return orderRepository.save(order);
    }

    private Order saveConfirmedOrder(OrderDto orderDto) {
        Order order = orderMapper.dtoToOrder(orderDto);
        Order save = orderRepository.save(order);
        return save;
    }
}
