package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.rest.BookRestController;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(BookRestController.class)
@Import(SecurityConfiguration.class)
public class BookRestControllerAuthenticationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;

    @WithMockUser(username = "administrator")
    @DisplayName("Проверка доступа для пользователя administrator")
    @Test
    public void authenticateAsAdministrator() throws Exception{
        mockMvc.perform(get("/book/all"))
                .andExpect(status().isOk());
    }

    @DisplayName("Проверка доступа без аутентификации")
    @Test
    public void unauthenticatedUser() throws Exception{
        mockMvc.perform(get("/book/all"))
                .andExpect(status().is3xxRedirection());
    }
}
