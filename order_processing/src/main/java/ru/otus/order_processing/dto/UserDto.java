package ru.otus.order_processing.dto;

public record UserDto(
        long id,

        String firstName,

        String middleName,


        String lastName,


        String phoneNumber
) {
}
