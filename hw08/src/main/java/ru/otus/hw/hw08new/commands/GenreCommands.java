package ru.otus.hw.hw08new.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.hw08new.services.GenreService;

@RequiredArgsConstructor
@ShellComponent
public class GenreCommands {
    private final GenreService genreService;

//    private final GenreConverter genreConverter;

    @ShellMethod(value = "Find all genres", key = "ag")
    public String findAllGenres() {
//        return genreService.findAll().stream()
//                .map(genreConverter::genreToString)
//                .collect(Collectors.joining("," + System.lineSeparator()));
        return null;
    }
}
