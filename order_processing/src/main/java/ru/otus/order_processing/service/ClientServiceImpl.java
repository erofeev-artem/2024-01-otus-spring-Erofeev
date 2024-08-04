package ru.otus.order_processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.order_processing.dto.ClientDto;
import ru.otus.order_processing.exception.EntityNotFoundException;
import ru.otus.order_processing.mapper.ClientMapper;
import ru.otus.order_processing.model.Client;
import ru.otus.order_processing.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }


    public Client save(Client client) {
        int passportSeries = client.getPassportSeries();
        int passportNumber = client.getPassportNumber();
        Optional<Client> optionalClient = clientRepository
                .findByPassportSeriesAndPassportNumber(passportSeries, passportNumber);
        if (optionalClient.isEmpty()) {
            return clientRepository.save(client);
        } else {
            Client existedClient = optionalClient.get();
            existedClient.setFirstName(client.getFirstName());
            existedClient.setMiddleName(client.getMiddleName());
            existedClient.setLastName(client.getLastName());
            existedClient.setPhoneNumber(client.getPhoneNumber());
            existedClient.setBirthDate(client.getBirthDate());
            existedClient.setBirthPlace(client.getBirthPlace());
            existedClient.setPassportSeries(client.getPassportSeries());
            existedClient.setPassportNumber(client.getPassportNumber());
            existedClient.setIssuedBy(client.getIssuedBy());
            return clientRepository.save(existedClient);
        }
    }

    @Override
    public Client dtoToEntity(ClientDto dto) {
        return clientMapper.dtoToClient(dto);
    }
}
