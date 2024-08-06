package ru.otus.order.mapper;

import org.mapstruct.Mapper;
import ru.otus.order.dto.OrderDto;
import ru.otus.order.kafka.OrderMessage;

@Mapper
public interface OrderMapper {

    OrderMessage dtoToMessage(OrderDto orderDto);
}
