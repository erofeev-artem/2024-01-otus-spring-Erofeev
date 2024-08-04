package ru.otus.order_processing.dto;

import ru.otus.order_processing.model.Engineer;
import ru.otus.order_processing.model.OrderStatus;

public record OrderDto(

        long id,

        TariffDto tariffDto,
        CustomerDto customerDto,

        ClientDto clientDto,

        ConnectionAddressDto connectionAddressDto,


        Engineer engineer,


        OrderStatus orderStatus,

        String comment
) {
}
