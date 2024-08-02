package ru.otus.order.dto;

public record OrderDto(long tariffId,

                       ClientDto clientDto,

                       ClientAddressDto clientAddressDto) {
}
