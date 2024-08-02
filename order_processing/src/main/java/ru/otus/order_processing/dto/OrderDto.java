package ru.otus.order_processing.dto;

public record OrderDto(

        long tariffId,

        ClientDto clientDto,

        ClientAddressDto clientAddressDto

) {
}
