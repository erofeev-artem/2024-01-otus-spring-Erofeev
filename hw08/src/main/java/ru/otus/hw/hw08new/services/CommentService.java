package ru.otus.hw.hw08new.services;


import ru.otus.hw.hw08new.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(String id);

    List<Comment> findByBookId(String id);

    Comment save(String id, String text, String bookId);

    void deleteById(String id);
}
