package ru.otus.order_processing.dto;

public record ConnectionAddressDto(

        long id,

        String city,

        String street,

        String house,

        String building,

        String structure,

        String apartment) {
}
