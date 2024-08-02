package ru.otus.order_processing.dto;

public record ClientAddressDto(String city,

                               String street,

                               String house,

                               String building,

                               String structure,

                               String apartment) {
}
