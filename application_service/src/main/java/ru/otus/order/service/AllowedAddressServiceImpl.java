package ru.otus.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.order.dto.ClientAddressDto;
import ru.otus.order.model.AllowedAddress;
import ru.otus.order.repository.AllowedAddressRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AllowedAddressServiceImpl implements AllowedAddressService {

    private final AllowedAddressRepository allowedAddressRepository;

    @Override
    public boolean checkHouseAddress(ClientAddressDto clientAddressDto) {
        Optional<AllowedAddress> optionalAllowedAddress = allowedAddressRepository
                .findByCityAndStreetAndHouseAndBuildingAndStructure(clientAddressDto.city(),
                        clientAddressDto.street(), clientAddressDto.house(),
                        clientAddressDto.building(), clientAddressDto.structure());
        return optionalAllowedAddress.isPresent();
    }
}
