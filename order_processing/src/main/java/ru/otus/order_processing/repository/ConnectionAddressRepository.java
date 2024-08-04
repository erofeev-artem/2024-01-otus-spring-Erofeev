package ru.otus.order_processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order_processing.model.ConnectionAddress;

public interface ConnectionAddressRepository extends JpaRepository<ConnectionAddress, Long> {


}
