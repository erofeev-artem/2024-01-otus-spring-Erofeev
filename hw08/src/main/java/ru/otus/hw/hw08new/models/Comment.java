package ru.otus.hw.hw08new.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    private String id;

    private String text;

    @DBRef
    private Book book;

    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }
}
