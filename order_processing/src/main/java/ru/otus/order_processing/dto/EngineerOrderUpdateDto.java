package ru.otus.order_processing.dto;

import ru.otus.order_processing.model.OrderStatus;

public record EngineerOrderUpdateDto(long orderId,
                                     TariffDto tariffDto,
                                     ClientDto clientDto,
                                     OrderStatus orderStatus,
                                     String comment) {
}
