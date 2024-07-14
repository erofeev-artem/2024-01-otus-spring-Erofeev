package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.models.Genre;
import ru.otus.hw.rest.GenreRestController;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер для работы с жанрами")
@WebMvcTest(GenreRestController.class)
@Import(SecurityConfiguration.class)
public class GenreRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @DisplayName("Получение всех жанров")
    @WithMockUser(username = "administrator")
    @Test
    void allGenres() throws Exception {
        Genre genre1 = new Genre(1, "Genre_1");
        Genre genre2 = new Genre(2, "Genre_2");
        when(genreService.findAll()).thenReturn(List.of(genre1, genre2));
        this.mockMvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("Genre_1")))
                .andExpect(content()
                        .string(containsString("Genre_2")));
    }
}
