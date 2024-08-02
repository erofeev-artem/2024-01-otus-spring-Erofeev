package ru.otus.order_processing.kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.order_processing.dto.OrderDto;
import ru.otus.order_processing.service.OrderService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderConsumerService implements OrderConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderConsumerService.class);

    private final OrderService orderService;

    @Override
    public void accept(List<OrderDto> orderDtos) {
        for (var orderDto : orderDtos) {
            LOG.info("log:{}", orderDto);
            orderService.save(orderDto);
        }
    }
}
