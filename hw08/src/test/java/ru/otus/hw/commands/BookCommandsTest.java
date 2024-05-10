package ru.otus.hw.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.converters.BookConverter;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.hw.utils.CommandsUtils.getIdFromBook;

@DisplayName("Команды для работы с книгами")
@SpringBootTest
public class BookCommandsTest {
    @Autowired
    private BookCommands bookCommands;
    @Autowired
    private AuthorCommands authorCommands;
    @Autowired
    private GenreCommands genreCommands;
    @Autowired
    private BookConverter bookConverter;

    @DisplayName("Получаем список всех книг")
    @Test
    public void findAllBooks() {
        String actualBooks = bookCommands.findAllBooks();
        assertThat(actualBooks).contains("Fahrenheit 451");
        assertThat(actualBooks).contains("Atlas shrugged");
        assertThat(actualBooks).contains("Starship troopers");
    }

    @DisplayName("Получаем книги по title и id")
    @Test
    public void findBookById() {
        String expectedBook = bookCommands.findBookByTitle("Atlas shrugged");
        var actualBook = bookCommands.findBookById(getIdFromBook(expectedBook));
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("Добавляем новую книгу")
    @Test
    public void insertBook() {
        String expectedBook = bookCommands.saveBook("Test title", "Author_1",
                Set.of("Genre_1", "Genre_2"));
        String actualBook = bookCommands.findBookByTitle("Test title");
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("Удаляем существующую книгу")
    @Test
    public void deleteBook() {
        bookCommands.saveBook("Test title 2", "Author_3",
                Set.of("Genre_3"));
        Assertions.assertFalse(bookCommands.findBookByTitle("Test title 2").isEmpty());
        bookCommands.deleteBook("Test title 2");
        String result = bookCommands.findBookById("Test title 2");
        assertThat(result).isEqualTo("Book with id Test title 2 not found");
    }
}
