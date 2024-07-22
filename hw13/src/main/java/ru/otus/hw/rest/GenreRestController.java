package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreRestController {
    private final GenreService genreService;

    @GetMapping
    public List<GenreDto> getGenres() {
        List<Genre> genreList = genreService.findAll();
        return genreList.stream().map(GenreDto::toDto).collect(Collectors.toList());
    }
}
