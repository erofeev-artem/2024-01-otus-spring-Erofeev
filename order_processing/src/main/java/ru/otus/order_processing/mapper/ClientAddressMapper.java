package ru.otus.order_processing.mapper;

import org.mapstruct.Mapper;
import ru.otus.order_processing.dto.ClientAddressDto;
import ru.otus.order_processing.model.ClientAddress;

@Mapper
public interface ClientAddressMapper {
    ClientAddressDto addressToDto(ClientAddress author);

    ClientAddress dtoToAddress(ClientAddressDto clientAddressDto);
}
