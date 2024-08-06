package ru.otus.order.dto;

public record AllowedAddressDto(String city,

                                String street,

                                String house,

                                String building,

                                String structure) {
}
