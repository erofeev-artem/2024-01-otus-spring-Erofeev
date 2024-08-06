package ru.otus.order_processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.order_processing.dto.ConnectionAddressDto;
import ru.otus.order_processing.mapper.ConnectionAddressMapper;
import ru.otus.order_processing.model.ConnectionAddress;
import ru.otus.order_processing.repository.ConnectionAddressRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ConnectionAddressServiceImpl implements ConnectionAddressService {

    private final ConnectionAddressRepository connectionAddressRepository;

    private final ConnectionAddressMapper connectionAddressMapper;

    @Transactional
    public ConnectionAddress save(ConnectionAddressDto connectionAddressDto) {
        Optional<ConnectionAddress> existedAddress = connectionAddressRepository
                .findByCityAndStreetAndHouseAndBuildingAndStructureAndApartment(connectionAddressDto.city(),
                        connectionAddressDto.street(), connectionAddressDto.house(), connectionAddressDto.building(),
                        connectionAddressDto.structure(), connectionAddressDto.apartment());

        ConnectionAddress savedConnectionAddress;
        if (existedAddress.isEmpty()) {
            ConnectionAddress connectionAddress = connectionAddressMapper.dtoToAddress(connectionAddressDto);
            savedConnectionAddress = connectionAddressRepository.save(connectionAddress);
        } else {
            savedConnectionAddress = existedAddress.get();
        }
        return connectionAddressRepository.save(savedConnectionAddress);
    }
}
