package ru.otus.order_processing.service;

import ru.otus.order_processing.dto.ConnectionAddressDto;
import ru.otus.order_processing.model.ConnectionAddress;

public interface ConnectionAddressService {

    ConnectionAddress save(ConnectionAddressDto connectionAddressDto);

}
