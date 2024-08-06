package ru.otus.order.dto;

public record ConnectionAddressDto(
        String city,

        String street,

        String house,

        String building,

        String structure,

        String apartment
) {
}
