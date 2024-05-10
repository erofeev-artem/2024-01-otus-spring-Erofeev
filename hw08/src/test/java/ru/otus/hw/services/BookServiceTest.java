package ru.otus.hw.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с книгами")
@SpringBootTest(classes = {BookServiceImpl.class})
public class BookServiceTest {
    @Autowired
    BookService bookService;
    @MockBean
    AuthorRepository mockAuthorRepository;
    @MockBean
    GenreRepository mockGenreRepository;
    @MockBean
    BookRepository mockBookRepository;

    @DisplayName("Получаем книгу по id")
    @Test
    public void findById() {
        Genre genre = new Genre("1", "Science fiction");
        Book book = new Book("123", "Dune", new Author("F Herbert"), Set.of(genre));
        when(mockBookRepository.findById("123")).thenReturn(Optional.of(book));
        Assertions.assertEquals("F Herbert", bookService.findById("123").get().getAuthor().getFullName());
        Assertions.assertEquals("Dune", bookService.findById("123").get().getTitle());
    }

    @DisplayName("Получаем книгу по title")
    @Test
    public void findByTitle() {
        Genre genre = new Genre("1", "Science fiction");
        Book book = new Book("456", "Dune 2", new Author("F Herbert"), Set.of(genre));
        when(mockBookRepository.findByTitle("Dune 2")).thenReturn(Optional.of(book));
        Assertions.assertEquals("456", bookService.findByTitle("Dune 2").get().getId());
    }

    @DisplayName("Получаем все книги")
    @Test
    public void findAll() {
        Genre genre = new Genre("1", "Science fiction");
        List<Book> books = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            books.add(new Book("456" + i, "Dune " + i, new Author("F Herbert"), Set.of(genre)));
        }
        when(mockBookRepository.findAll()).thenReturn(books);
        Assertions.assertEquals(3, bookService.findAll().size());
        Assertions.assertEquals("Dune 3", bookService.findAll().get(2).getTitle());
    }

    @DisplayName("Сохраняем, а затем обновляем книгу")
    @Test
    public void saveOrUpdate() {
        Genre genre = new Genre("1", "Science fiction");
        Genre genre2 = new Genre("2", "Fantasy");
        Author author = new Author("1", "F Herbert");
        Author author2 = new Author("2", "H Wells");
        Book book = new Book("789", "Dune 3", author, Set.of(genre));
        Book book2 = new Book("789", "Dune 3", author2, Set.of(genre2));

        when(mockBookRepository.findByTitle("Dune 3")).thenReturn(Optional.of(book));
        when(mockAuthorRepository.findByFullName("F Herbert")).thenReturn(Optional.of(author));
        when(mockAuthorRepository.findByFullName("H Wells")).thenReturn(Optional.of(author2));
        when(mockGenreRepository.findByName("Science fiction")).thenReturn(Optional.of(genre));
        when(mockGenreRepository.findByName("Fantasy")).thenReturn(Optional.of(genre2));
        when(mockBookRepository.save(book)).thenReturn(book);
        when(mockBookRepository.save(book2)).thenReturn(book2);

        Assertions.assertEquals("789",
                bookService.saveOrUpdate("Dune 3", "F Herbert", Set.of("Science fiction")).getId());
        Assertions.assertEquals("789",
                bookService.saveOrUpdate("Dune 3", "H Wells", Set.of("Fantasy")).getId());
    }
}
