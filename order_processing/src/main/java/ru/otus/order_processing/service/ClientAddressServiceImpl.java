package ru.otus.order_processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.order_processing.dto.ClientAddressDto;
import ru.otus.order_processing.mapper.ClientAddressMapper;
import ru.otus.order_processing.model.ClientAddress;
import ru.otus.order_processing.repository.ClientAddressRepository;

@RequiredArgsConstructor
@Service
public class ClientAddressServiceImpl implements ClientAddressService {

    private final ClientAddressRepository clientAddressRepository;

    private final ClientAddressMapper clientAddressMapper;

    @Transactional
    public ClientAddress save(ClientAddressDto clientAddressDto) {
        ClientAddress clientAddress = clientAddressMapper.dtoToAddress(clientAddressDto);
        return clientAddressRepository.save(clientAddress);
    }
}
