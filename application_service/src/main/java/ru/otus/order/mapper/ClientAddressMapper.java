package ru.otus.order.mapper;

import org.mapstruct.Mapper;
import ru.otus.order.dto.ClientAddressDto;
import ru.otus.order.model.ClientAddress;

@Mapper
public interface ClientAddressMapper {

    ClientAddress dtoToAddress(ClientAddressDto clientAddressDto);
}
