package ru.otus.hw.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с комментариями")
@SpringBootTest(classes = {CommentServiceImpl.class})
public class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @MockBean
    BookRepository mockBookRepository;
    @MockBean
    CommentRepository mockCommentRepository;

    @DisplayName("Получаем комментарий по id книги")
    @Test
    public void findByBookId() {
        Comment comment = new Comment("test comment", new Book());
        when(mockCommentRepository.findById("abc123")).thenReturn(Optional.of(comment));
        Assertions.assertTrue(commentService.findById("abc123").isPresent());
    }

    @DisplayName("Получаем комментарий по title книги")
    @Test
    public void findByBookTitle() {
        Genre genre = new Genre("1", "Science fiction");
        Book book = new Book("zxc789", "Dune", new Author("F Herbert"), Set.of(genre));
        Comment comment = new Comment("test comment 1", book);
        Comment comment2 = new Comment("test comment 2", book);
        when(mockCommentRepository.findByBookId("zxc789")).thenReturn(List.of(comment, comment2));
        when(mockBookRepository.findByTitle("Dune")).thenReturn(Optional.of(book));
        Assertions.assertEquals(2, commentService.findByBookTitle("Dune").size());
    }

    @DisplayName("Сохраняем комментарий")
    @Test
    public void save() {
        Genre genre = new Genre("123", "Science fiction");
        Book book = new Book("asd456", "Dune 2", new Author("F Herbert"), Set.of(genre));
        Comment comment = new Comment("comm123", "test comment 2", book);
        when(mockCommentRepository.save(any(Comment.class))).thenReturn(comment);
        when(mockBookRepository.findByTitle("Dune 2")).thenReturn(Optional.of(book));
        Assertions.assertEquals("comm123", commentService.save("test comment 2", "Dune 2")
                .getId());
    }

    @DisplayName("Изменяем комментарий")
    @Test
    public void update() {
        Comment comment = new Comment("mkl567", "test comment 3", new Book());
        when(mockCommentRepository.findById("mkl567")).thenReturn(Optional.of(comment));
        when(mockCommentRepository.save(any(Comment.class))).thenReturn(comment);
        Assertions.assertEquals("mkl567", commentService.update("mkl567", "test comment 3").getId());
    }
}
