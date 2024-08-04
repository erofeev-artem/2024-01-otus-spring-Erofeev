package ru.otus.order_processing.mapper;

import org.mapstruct.Mapper;
import ru.otus.order_processing.dto.TariffDto;
import ru.otus.order_processing.model.Tariff;

@Mapper
public interface TariffMapper {
    TariffDto tariffToDto(Tariff tariff);

    Tariff dtoToTariff(TariffDto tariffDto);
}
