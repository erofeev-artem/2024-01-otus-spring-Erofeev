package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
@DisplayName("Репозиторий для работы с авторами")
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Получение автора по названию")
    @Test
    void findAuthorByFullName() {
        Assertions.assertTrue(authorRepository.findByFullName("Author_1").isPresent());
    }
}
