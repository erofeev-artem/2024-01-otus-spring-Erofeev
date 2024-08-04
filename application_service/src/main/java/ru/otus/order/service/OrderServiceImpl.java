package ru.otus.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.order.dto.OrderDto;
import ru.otus.order.kafka.DataProducer;
import ru.otus.order.kafka.OrderMessage;
import ru.otus.order.mapper.OrderMapper;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final DataProducer dataProducer;

    private final OrderMapper orderMapper;


    @Override
    public void send(OrderDto orderDto) {
        OrderMessage orderMessage = orderMapper.dtoToMessage(orderDto);
        dataProducer.send(orderMessage);
    }
}
