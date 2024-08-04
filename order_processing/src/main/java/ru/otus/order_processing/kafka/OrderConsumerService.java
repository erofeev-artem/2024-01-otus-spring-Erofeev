package ru.otus.order_processing.kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.order_processing.dto.OrderDto;
import ru.otus.order_processing.dto.TariffDto;
import ru.otus.order_processing.mapper.OrderMapper;
import ru.otus.order_processing.model.OrderStatus;
import ru.otus.order_processing.service.OrderService;
import ru.otus.order_processing.service.TariffService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderConsumerService implements OrderConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderConsumerService.class);

    private final OrderService orderService;
    private final TariffService tariffService;

    private final OrderMapper orderMapper;

    @Override
    public void accept(List<OrderMessage> orderMessages) {
        for (var orderMessage : orderMessages) {
            LOG.info("log:{}", orderMessage);
            TariffDto tariffDto = tariffService.findById(orderMessage.tariffId());
            OrderDto orderDto = orderMapper.messageToDto(orderMessage, tariffDto, OrderStatus.NEW);
            orderService.save(orderDto);
        }
    }
}
