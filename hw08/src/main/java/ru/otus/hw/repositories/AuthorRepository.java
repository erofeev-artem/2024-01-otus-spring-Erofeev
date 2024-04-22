package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.Author;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Author findById(String id);

    @Override
    List<Author> findAll();
}
