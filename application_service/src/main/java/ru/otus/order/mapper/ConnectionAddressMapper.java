package ru.otus.order.mapper;

import org.mapstruct.Mapper;
import ru.otus.order.dto.ConnectionAddressDto;
import ru.otus.order.model.ConnectionAddress;

@Mapper
public interface ConnectionAddressMapper {

    ConnectionAddress dtoToAddress(ConnectionAddressDto connectionAddressDto);
}
