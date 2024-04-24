package ru.otus.hw.hw08new.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.hw08new.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findById(String id);

    @Override
    List<Author> findAll();
}
