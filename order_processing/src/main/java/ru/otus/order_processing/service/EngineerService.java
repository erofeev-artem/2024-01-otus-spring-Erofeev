package ru.otus.order_processing.service;

import ru.otus.order_processing.dto.EngineerDto;

import java.util.List;

public interface EngineerService {
    public List<EngineerDto> findAllEngineers();
}
