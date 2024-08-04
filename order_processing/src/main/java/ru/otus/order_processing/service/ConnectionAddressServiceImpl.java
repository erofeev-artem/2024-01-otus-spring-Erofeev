package ru.otus.order_processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.order_processing.dto.ConnectionAddressDto;
import ru.otus.order_processing.mapper.ConnectionAddressMapper;
import ru.otus.order_processing.model.ConnectionAddress;
import ru.otus.order_processing.repository.ConnectionAddressRepository;

@RequiredArgsConstructor
@Service
public class ConnectionAddressServiceImpl implements ConnectionAddressService {

    private final ConnectionAddressRepository connectionAddressRepository;

    private final ConnectionAddressMapper connectionAddressMapper;

    @Transactional
    public ConnectionAddress save(ConnectionAddressDto connectionAddressDto) {
        ConnectionAddress connectionAddress = connectionAddressMapper.dtoToAddress(connectionAddressDto);
        return connectionAddressRepository.save(connectionAddress);
    }
}
