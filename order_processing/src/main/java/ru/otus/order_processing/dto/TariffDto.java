package ru.otus.order_processing.dto;

public record TariffDto(
        String name,

        double speed,

        double price,

        boolean archived
) {
}
