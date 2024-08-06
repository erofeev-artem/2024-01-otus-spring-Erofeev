package ru.otus.order_processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.order_processing.dto.TariffDto;
import ru.otus.order_processing.exception.EntityNotFoundException;
import ru.otus.order_processing.mapper.TariffMapper;
import ru.otus.order_processing.model.Tariff;
import ru.otus.order_processing.repository.TariffRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;

    private final TariffMapper tariffMapper;

    @Override
    public List<TariffDto> findActual() {
        List<Tariff> actualTariffs = tariffRepository.findByArchivedFalse();
        return actualTariffs.stream().map(tariffMapper::tariffToDto).toList();
    }

    @Override
    public TariffDto findById(long id) {
        Tariff tariff = tariffRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Tariff with id %d not found".formatted(id)));
        return tariffMapper.tariffToDto(tariff);
    }

    @Override
    public Tariff findByNameAndArchived(String name, boolean archived) {
        return tariffRepository.findByNameAndArchived(name, archived)
                .orElseThrow(()
                        -> new EntityNotFoundException("Tariff with name %s and archived status %b not found"
                        .formatted(name, archived)));
    }

    @Override
    public Tariff dtoToEntity(TariffDto dto) {
        return tariffMapper.dtoToTariff(dto);
    }
}
