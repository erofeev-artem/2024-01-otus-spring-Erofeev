package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.hw.models.Genre;
import ru.otus.hw.rest.GenreRestController;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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

    static List<Object[]> authenticationParameters() {
        ArrayList<Object[]> parameters = new ArrayList<>();
        parameters.add(new Object[]{"administrator", "admin", status().isOk(), "Genre_1"});
        parameters.add(new Object[]{"user", "user", status().isForbidden(), "Access denied"});
        return parameters;
    }

    @DisplayName("Получение всех жанров с ролями admin и user")
    @ParameterizedTest
    @MethodSource("authenticationParameters")
    void allGenres(String username, String role, ResultMatcher status, String expectedString) throws Exception {
        Genre genre1 = new Genre(1, "Genre_1");
        Genre genre2 = new Genre(2, "Genre_2");
        when(genreService.findAll()).thenReturn(List.of(genre1, genre2));
        this.mockMvc.perform(get("/genres")
                        .with(user(username).roles(role)))
                .andExpect(status)
                .andExpect(content()
                        .string(containsString(expectedString)));
    }

    @DisplayName("Проверка доступа без аутентификации")
    @Test
    public void unauthenticatedUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/genres")).andExpect(status().isFound());
    }
}
