package ru.otus.order_processing.kafka;

import ru.otus.order_processing.dto.ConnectionAddressDto;
import ru.otus.order_processing.dto.CustomerDto;

public record OrderMessage (
        long tariffId,

        CustomerDto customerDto,

        ConnectionAddressDto connectionAddressDto
){
}
