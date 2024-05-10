package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;

@DataMongoTest
@DisplayName("Репозиторий для работы с комментариями")
public class CommentRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("Получение всех комментариев по id книги")
    @Test
    void findCommentsByBookId() {
        Book book = bookRepository.findByTitle("Atlas shrugged").get();
        List<Comment> comments = commentRepository.findByBookId(book.getId());
        Assertions.assertEquals(3, comments.size());
        Assertions.assertEquals("Awesome", comments.get(0).getText());
        Assertions.assertEquals("Interesting", comments.get(1).getText());
    }

    @DisplayName("Получение комментария по id")
    @Test
    void findCommentById() {
        List<Comment> comments = commentRepository.findAll();
        Comment comment = commentRepository.findById(comments.get(0).getId()).get();
        Assertions.assertEquals("Not bad", comment.getText());
    }

    @DisplayName("Добавление нового комментария")
    @Test
    void addComment() {
        Book book = bookRepository.findByTitle("Atlas shrugged").get();
        var expectedComment = new Comment("The Best", book);
        commentRepository.save(expectedComment);
        List<Comment> comments = commentRepository.findByBookId(book.getId());
        Assertions.assertEquals(3, comments.size());
        Assertions.assertTrue(comments.stream().anyMatch(c -> c.getText().equals("The Best")));
    }

    @DisplayName("Удаление комментария")
    @Test
    void deleteComment() {
        Book book = bookRepository.findByTitle("Starship troopers").get();
        commentRepository.deleteByBookId(book.getId());
        List<Comment> comments = commentRepository.findByBookId(book.getId());
        Assertions.assertEquals(0, comments.size());
    }
}
