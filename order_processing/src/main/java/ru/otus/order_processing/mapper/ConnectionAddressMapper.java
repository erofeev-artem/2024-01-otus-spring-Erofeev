package ru.otus.order_processing.mapper;

import org.mapstruct.Mapper;
import ru.otus.order_processing.dto.ConnectionAddressDto;
import ru.otus.order_processing.model.ConnectionAddress;

@Mapper
public interface ConnectionAddressMapper {
    ConnectionAddressDto addressToDto(ConnectionAddress author);

    ConnectionAddress dtoToAddress(ConnectionAddressDto connectionAddressDto);
}
