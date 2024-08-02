package ru.otus.order_processing.dto;

public record ClientDto(
        String firstName,

        String middleName,

        String lastName,

        String phoneNumber
) {
}
