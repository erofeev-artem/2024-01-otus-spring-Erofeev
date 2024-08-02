package ru.otus.order_processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order_processing.model.ClientAddress;

public interface ClientAddressRepository extends JpaRepository<ClientAddress, Long> {


}
