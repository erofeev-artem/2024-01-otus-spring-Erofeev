package ru.otus.order_processing.dto;

public record TariffDto(

        long id,

        String name,

        double speed,

        double price,

        boolean archived
) {
}
