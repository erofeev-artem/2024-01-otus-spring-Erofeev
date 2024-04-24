package ru.otus.hw.hw08new.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.hw08new.models.Genre;

@Component
public class GenreConverter {
    public String genreToString(Genre genre) {
        return "Id: %s, Name: %s".formatted(genre.getId(), genre.getName());
    }
}
