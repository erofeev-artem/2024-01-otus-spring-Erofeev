package ru.otus.order_processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order_processing.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
