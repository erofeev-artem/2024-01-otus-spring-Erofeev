package ru.otus.hw.hw08new.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.hw08new.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findById(Long id);

    List<Book> findAll();
}
