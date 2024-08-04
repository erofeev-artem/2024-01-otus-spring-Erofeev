package ru.otus.order_processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.order_processing.dto.EngineerDto;
import ru.otus.order_processing.mapper.EngineerMapper;
import ru.otus.order_processing.model.Engineer;
import ru.otus.order_processing.repository.EngineerRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EngineerServiceImpl implements EngineerService {

    private final EngineerRepository engineerRepository;

    private final EngineerMapper engineerMapper;

    public List<EngineerDto> findAllEngineers() {
        return engineerRepository.findAll().stream().map(engineerMapper::entityToDto).toList();
    }
}
