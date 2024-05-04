package ru.otus.hw.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommentCascadeSaveEventsListener  extends AbstractMongoEventListener<Comment> {
    private final BookRepository bookRepository;

    @Override
    public void onBeforeSave(BeforeSaveEvent<Comment> event) {
        super.onBeforeSave(event);

//        Comment comment = event.getSource();
//        bookRepository.findByTitle()
//        if (book.getComments() != null) {
//            book.getComments().stream().filter(comment -> Objects.isNull(comment.getId()))
//                    .forEach(commentRepository::save);
//        }
    }
}
