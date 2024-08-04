package ru.otus.order_processing.service;


import ru.otus.order_processing.dto.TariffDto;
import ru.otus.order_processing.model.Tariff;

import java.util.List;
import java.util.Optional;

public interface TariffService {

    List<TariffDto> findActual();

    TariffDto findById(long id);

    Tariff findByNameAndArchived(String name, boolean archived);

    Tariff dtoToEntity(TariffDto dto);
}
