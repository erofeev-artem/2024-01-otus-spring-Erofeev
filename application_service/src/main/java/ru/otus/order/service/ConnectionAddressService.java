package ru.otus.order.service;

import ru.otus.order.dto.ConnectionAddressDto;

public interface ConnectionAddressService {

    boolean checkApartmentAddress(ConnectionAddressDto connectionAddressDto);
}
