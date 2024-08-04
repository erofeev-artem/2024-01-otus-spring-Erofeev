package ru.otus.order_processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order_processing.model.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {


}
