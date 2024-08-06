package ru.otus.order_processing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.order_processing.dto.OrderDto;
import ru.otus.order_processing.dto.TariffDto;
import ru.otus.order_processing.kafka.OrderMessage;
import ru.otus.order_processing.model.Order;
import ru.otus.order_processing.model.OrderStatus;

@Mapper
public interface OrderMapper {

    @Mapping(target = "customerDto", source = "customer")
    @Mapping(target = "connectionAddressDto", source = "connectionAddress")
    @Mapping(target = "tariffDto", source = "tariff")
    @Mapping(target = "clientDto", source = "client")
    @Mapping(target = "userDto", source = "user")
    OrderDto orderToDto(Order order);

    @Mapping(target = "client", source = "clientDto")
    @Mapping(target = "connectionAddress", source = "connectionAddressDto")
    @Mapping(target = "tariff", source = "tariffDto")
    @Mapping(target = "customer", source = "customerDto")
    @Mapping(target = "user", source = "userDto")
    Order dtoToOrder(OrderDto orderDto);

    @Mapping(source = "tariffDto", target = "tariffDto")
    @Mapping(target = "orderStatus", expression = "java(status.NEW)")
    OrderDto messageToDto(OrderMessage orderMessage, TariffDto tariffDto, OrderStatus status);
}
