package ru.otus.order.service;

import ru.otus.order.dto.ClientAddressDto;

public interface AllowedAddressService {
    boolean checkHouseAddress(ClientAddressDto clientAddressDto);
}
