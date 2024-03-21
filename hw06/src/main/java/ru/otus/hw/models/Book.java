package ru.otus.hw.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @ManyToOne()
    private Author author;

    @ManyToMany
    @JoinColumn(name = "genres", joinColumns = @JoinColumn(name="genre_id"), inverseJoinColumns = @JoinColumn(name ='id'))
    private List<Genre> genres;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public boolean isEmpty() {
        return id == 0 && title == null && author == null && genres.isEmpty() && comment == null;
    }
}
