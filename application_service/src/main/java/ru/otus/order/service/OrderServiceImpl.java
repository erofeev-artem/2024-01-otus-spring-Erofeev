package ru.otus.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.order.dto.OrderDto;
import ru.otus.order.kafka.DataProducer;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final DataProducer dataProducer;


    @Override
    public void send(OrderDto orderDto) {
        dataProducer.send(orderDto);
    }
}
