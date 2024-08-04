package ru.otus.order.kafka;

import ru.otus.order.dto.CustomerDto;
import ru.otus.order.dto.ConnectionAddressDto;

public record OrderMessage(long tariffId,

                           CustomerDto customerDto,

                           ConnectionAddressDto connectionAddressDto) {
}
