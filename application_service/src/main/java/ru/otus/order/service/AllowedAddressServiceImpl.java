package ru.otus.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.order.dto.ConnectionAddressDto;
import ru.otus.order.model.AllowedAddress;
import ru.otus.order.repository.AllowedAddressRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AllowedAddressServiceImpl implements AllowedAddressService {

    private final AllowedAddressRepository allowedAddressRepository;

    @Override
    public boolean checkHouseAddress(ConnectionAddressDto connectionAddressDto) {
        Optional<AllowedAddress> optionalAllowedAddress = allowedAddressRepository
                .findByCityAndStreetAndHouseAndBuildingAndStructure(connectionAddressDto.city(),
                        connectionAddressDto.street(), connectionAddressDto.house(),
                        connectionAddressDto.building(), connectionAddressDto.structure());
        return optionalAllowedAddress.isPresent();
    }
}
