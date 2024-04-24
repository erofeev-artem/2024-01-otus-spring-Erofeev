package ru.otus.hw.hw08new.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.hw08new.converters.BookConverter;
import ru.otus.hw.hw08new.models.Author;
import ru.otus.hw.hw08new.models.Book;
import ru.otus.hw.hw08new.models.Genre;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Команды для работы с книгами")
@SpringBootTest
public class BookCommandsTest {
    @Autowired
    private BookCommands bookCommands;
    @Autowired
    private BookConverter bookConverter;

    private List<Author> dbAuthors;
    private List<Genre> dbGenres;
    private List<Book> dbBooks;

    @DisplayName("Получаем список всех книг")
    @Test
    public void findAllBooks() {
        String actualBooks = bookCommands.findAllBooks();
        assertThat(actualBooks).contains("Fahrenheit 451");
        assertThat(actualBooks).contains("Atlas shrugged");
        assertThat(actualBooks).contains("Starship troopers");
    }

    @DisplayName("Получаем книги по id")
    @Test
    public void findBookById(Book expectedBook) {
        var actualBook = bookCommands.findBookById(expectedBook.getId());
        assertThat(actualBook).isEqualTo(bookConverter.bookToString(expectedBook));
    }

    @DisplayName("Добавляем новую книгу")
    @Test
    public void insertBook() {
        String expectedBook = bookCommands.insertBook("Test title", "2", Set.of("2", "3"));
        String bookId = expectedBook.substring(4, 5);
        String actualBook = bookCommands.findBookById(bookId);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("Изменяем существующую книгу")
    @Test
    public void updateBook() {
        String expectedBook = bookCommands.updateBook("3", "Test title", "2", Set.of("2", "3"));
        String actualBook = bookCommands.findBookById("3");
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("Удаляем существующую книгу")
    @Test
    public void deleteBook() {
        bookCommands.deleteBook("2");
        String result = bookCommands.findBookById("2");
        assertThat(result).isEqualTo("Book with id 2 not found");
    }
}
