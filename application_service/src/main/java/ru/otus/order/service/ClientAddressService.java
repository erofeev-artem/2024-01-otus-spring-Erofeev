package ru.otus.order.service;

import ru.otus.order.dto.ClientAddressDto;

public interface ClientAddressService {

    boolean checkApartmentAddress(ClientAddressDto clientAddressDto);
}
