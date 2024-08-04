package ru.otus.order_processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.order_processing.dto.TariffDto;
import ru.otus.order_processing.exception.EntityNotFoundException;
import ru.otus.order_processing.mapper.TariffMapper;
import ru.otus.order_processing.model.Tariff;
import ru.otus.order_processing.repository.TariffRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;

    private final TariffMapper tariffMapper;

    public List<TariffDto> findActual() {
        List<Tariff> actualTariffs = tariffRepository.findByArchivedFalse();
//        return actualTariffs.stream().map(tariffMapper::tariffToDto)
//                .collect(Collectors.toList());
        return null;
    }

    public TariffDto findById(long id) {
        Tariff tariff = tariffRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Tariff with id %d not found".formatted(id)));
        return tariffMapper.tariffToDto(tariff);
    }
}
