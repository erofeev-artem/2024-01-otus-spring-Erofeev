package ru.otus.hw.hw08new.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.hw08new.models.Comment;

@Component
public class CommentConverter {
    public String commentToString(Comment comment) {
        return "Id: %s, Text: %s".formatted(comment.getId(), comment.getText());
    }
}
