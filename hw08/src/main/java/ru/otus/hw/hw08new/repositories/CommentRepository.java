package ru.otus.hw.hw08new.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.hw08new.models.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByBookId(String bookId);
}
