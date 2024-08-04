package ru.otus.order_processing.dto;

import ru.otus.order_processing.model.OrderStatus;

import java.time.LocalDate;

public record OrderDto(
        long id,
        TariffDto tariffDto,
        CustomerDto customerDto,
        ClientDto clientDto,
        ConnectionAddressDto connectionAddressDto,
        EngineerDto engineer,
        OrderStatus orderStatus,
        LocalDate connectionDate,
        String comment
) {
}
