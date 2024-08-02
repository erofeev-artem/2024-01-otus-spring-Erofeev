package ru.otus.order_processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.order_processing.dto.ClientAddressDto;
import ru.otus.order_processing.dto.OrderDto;
import ru.otus.order_processing.mapper.ClientAddressMapper;
import ru.otus.order_processing.mapper.ClientMapper;
import ru.otus.order_processing.mapper.OrderMapper;
import ru.otus.order_processing.model.*;
import ru.otus.order_processing.repository.ClientAddressRepository;
import ru.otus.order_processing.repository.ClientRepository;
import ru.otus.order_processing.repository.OrderRepository;
import ru.otus.order_processing.repository.TariffRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ClientRepository clientRepository;

    private final ClientAddressRepository clientAddressRepository;

    private final TariffRepository tariffRepository;

    private final ClientMapper clientMapper;

    private final ClientAddressMapper clientAddressMapper;

    @Transactional
    @Override
    public void save(OrderDto orderDto) {
        ClientAddressDto clientAddressDto = orderDto.clientAddressDto();
        ClientAddress clientAddress = clientAddressMapper.dtoToAddress(clientAddressDto);
        clientAddressRepository.save(clientAddress);

        Client client = clientMapper.dtoToClient(orderDto.clientDto());
        clientRepository.save(client);

//        var createdDate = LocalDateTime.now();

        Optional<Tariff> tariff = tariffRepository.findById(orderDto.tariffId());

        Order order;
        if (tariff.isPresent()) {
            order = new Order(0, tariff.get(), client, clientAddress, OrderStatus.NEW, null, null, null);
        } else {
            throw new RuntimeException("Tariff not found");
        }
        orderRepository.save(order);
    }

//    public void setEngineer(O){
//        orderRepository.save()

//    }
}
