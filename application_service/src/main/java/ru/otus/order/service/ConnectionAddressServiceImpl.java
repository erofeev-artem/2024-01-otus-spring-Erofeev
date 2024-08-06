package ru.otus.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.order.dto.ConnectionAddressDto;
import ru.otus.order.mapper.ConnectionAddressMapper;
import ru.otus.order.model.AllowedAddress;
import ru.otus.order.model.ConnectionAddress;
import ru.otus.order.repository.AllowedAddressRepository;
import ru.otus.order.repository.ConnectionAddressRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ConnectionAddressServiceImpl implements ConnectionAddressService {

    private final ConnectionAddressRepository connectionAddressRepository;

    private final AllowedAddressRepository allowedAddressRepository;

    private final ConnectionAddressMapper connectionAddressMapper;

    public boolean checkApartmentAddress(ConnectionAddressDto connectionAddressDto) {
        ConnectionAddress connectionAddress = connectionAddressMapper.dtoToAddress(connectionAddressDto);
        Optional<AllowedAddress> existingAllowedAddress = allowedAddressRepository
                .findByCityAndStreetAndHouseAndBuildingAndStructure(connectionAddressDto.city(),
                        connectionAddressDto.street(), connectionAddressDto.house(),
                        connectionAddressDto.building(), connectionAddressDto.structure());
        return existingAllowedAddress.filter(address -> connectionAddressRepository.findByAllowedAddressIdAndApartment(
                address.getId(), connectionAddress.getApartment()).isPresent()).isPresent();
    }
}
