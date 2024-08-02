package ru.otus.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.order.dto.ClientAddressDto;
import ru.otus.order.mapper.ClientAddressMapper;
import ru.otus.order.model.AllowedAddress;
import ru.otus.order.model.ClientAddress;
import ru.otus.order.repository.AllowedAddressRepository;
import ru.otus.order.repository.ClientAddressRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClientAddressServiceImpl implements ClientAddressService {

    private final ClientAddressRepository clientAddressRepository;

    private final AllowedAddressRepository allowedAddressRepository;

    private final ClientAddressMapper clientAddressMapper;

    public boolean checkApartmentAddress(ClientAddressDto clientAddressDto) {
        ClientAddress clientAddress = clientAddressMapper.dtoToAddress(clientAddressDto);
        Optional<AllowedAddress> existingAllowedAddress = allowedAddressRepository
                .findByCityAndStreetAndHouseAndBuildingAndStructure(clientAddressDto.city(),
                        clientAddressDto.street(), clientAddressDto.house(),
                        clientAddressDto.building(), clientAddressDto.structure());
        return existingAllowedAddress.filter(address -> clientAddressRepository.findByAllowedAddressIdAndApartment(
                address.getId(), clientAddress.getApartment()).isPresent()).isPresent();
    }
}
