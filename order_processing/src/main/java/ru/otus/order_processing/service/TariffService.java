package ru.otus.order_processing.service;


import ru.otus.order_processing.dto.TariffDto;

import java.util.List;

public interface TariffService {

    List<TariffDto> findActual();
}
