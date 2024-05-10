package ru.otus.hw.services;


import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(String id);

    List<Comment> findByBookTitle(String title);

    Comment save(String text, String bookTitle);

    Comment update(String id, String text);

    void deleteById(String id);
}
