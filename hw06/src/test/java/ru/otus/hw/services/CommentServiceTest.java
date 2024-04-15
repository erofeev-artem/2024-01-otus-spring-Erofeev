package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с комментариями")
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService commentService;
    @Mock
    Comment comment;
    @Mock
    Book book;

    @DisplayName("Получаем комментарий по id книги")
    @Test
    public void findByBookId() {
        when(comment.getText()).thenReturn("Very interesting book");
        List<Comment> actualComments = commentService.findByBookId(2);
        String actualText = actualComments.get(1).getText();
        assertThat(actualText).isEqualTo(comment.getText());
    }

    @DisplayName("Сохраняем комментарий")
    @Test
    public void save(){
        when(comment.getId()).thenReturn(5L);
        when(comment.getText()).thenReturn("Old comment");
        when(book.getId()).thenReturn(3L);
        Comment actualComment = commentService.save(5, "Old comment", 3);
        assertThat(actualComment.getText()).isEqualTo(comment.getText());
        assertThat(actualComment.getId()).isEqualTo(comment.getId());
        assertThat(actualComment.getBook().getId()).isEqualTo(book.getId());
    }
}

