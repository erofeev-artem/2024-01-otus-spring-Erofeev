package ru.otus.order_processing.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClientDto(
        String firstName,

        String middleName,

        String lastName,

        String phoneNumber,

        LocalDate birthDate,

        String birthPlace,

        int passportSeries,

        int passportNumber,

        String issuedBy,

        LocalDateTime createdAt,

        LocalDateTime modifiedAt
) {
}
