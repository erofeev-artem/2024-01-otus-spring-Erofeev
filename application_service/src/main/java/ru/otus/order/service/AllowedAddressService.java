package ru.otus.order.service;

import ru.otus.order.dto.ConnectionAddressDto;

public interface AllowedAddressService {
    boolean checkHouseAddress(ConnectionAddressDto connectionAddressDto);
}
