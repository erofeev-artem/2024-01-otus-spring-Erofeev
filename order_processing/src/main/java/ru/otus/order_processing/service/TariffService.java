package ru.otus.order_processing.service;


import ru.otus.order_processing.dto.TariffDto;

import java.util.List;
import java.util.Optional;

public interface TariffService {

    List<TariffDto> findActual();

    TariffDto findById(long id);
}
