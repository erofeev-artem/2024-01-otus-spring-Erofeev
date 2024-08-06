package ru.otus.order_processing.dto;

public record CustomerDto(
        long id,
        String firstName,

        String middleName,

        String lastName,

        String phoneNumber) {
}
