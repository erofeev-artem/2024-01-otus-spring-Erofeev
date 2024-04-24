package ru.otus.hw.hw08new.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.hw08new.models.Author;


@Component
public class AuthorConverter {
    public String authorToString(Author author) {
        return "Id: %s, FullName: %s".formatted(author.getId(), author.getFullName());
    }
}
