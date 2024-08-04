package ru.otus.order_processing.service;

import ru.otus.order_processing.dto.ClientDto;
import ru.otus.order_processing.model.Client;

public interface ClientService {
    Client dtoToEntity(ClientDto dto);

    Client save(Client client);
}
