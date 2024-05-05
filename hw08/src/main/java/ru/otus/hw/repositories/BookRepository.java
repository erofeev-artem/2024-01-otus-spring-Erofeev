package ru.otus.hw.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> findByTitle(String title);

    void deleteBookByTitle(String title);

    void removeCommentArrayElementById(String id);

    List<Book> findAll();
}
