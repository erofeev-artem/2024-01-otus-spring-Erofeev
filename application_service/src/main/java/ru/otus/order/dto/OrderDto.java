package ru.otus.order.dto;

public record OrderDto(long tariffId,

                       CustomerDto customerDto,

                       ConnectionAddressDto connectionAddressDto) {
}
