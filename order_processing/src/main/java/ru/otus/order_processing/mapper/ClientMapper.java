package ru.otus.order_processing.mapper;

import org.mapstruct.Mapper;
import ru.otus.order_processing.dto.ClientDto;
import ru.otus.order_processing.model.Client;

@Mapper
public interface ClientMapper {
    ClientDto clientToDto(Client client);

    Client dtoToClient(ClientDto clientDto);
}
