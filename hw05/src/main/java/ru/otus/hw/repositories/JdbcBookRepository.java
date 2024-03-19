package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final GenreRepository genreRepository;

    private final JdbcOperations jdbc;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(jdbc.query("SELECT books.id AS book_id, title, authors.full_name, " +
                "authors.id AS author_id, genres.id AS genre_id, genres.name AS genre_name FROM books " +
                "LEFT JOIN books_genres ON books.id = books_genres.book_id  " +
                "LEFT JOIN genres ON books_genres.genre_id = genres.id " +
                "LEFT JOIN authors ON books.author_id = authors.id " +
                "WHERE books.id =?", new BookResultSetExtractor(), id));
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        jdbc.update("DELETE FROM books WHERE id = ?", id);
    }

    private List<Book> getAllBooksWithoutGenres() {
        return jdbc.query("SELECT books.id, title, authors.full_name, authors.id AS author_id " +
                "FROM books " +
                "LEFT JOIN authors " +
                "ON books.author_id = authors.id", new BookRowMapper());
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return jdbc.query("SELECT book_id, genre_id FROM books_genres",
                (rs, rowNum) -> new BookGenreRelation(rs.getLong("book_id"),
                        rs.getLong("genre_id")));
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {
        // Добавить книгам (booksWithoutGenres) жанры (genres) в соответствии со связями (relations)
        for (Book book : booksWithoutGenres) {
            var id = book.getId();
            List<Long> necessaryGenresIds = relations.stream().filter(relation -> relation.bookId == id)
                    .map(BookGenreRelation::genreId)
                    .collect(Collectors.toList());

            List<Genre> necessaryGenres = genres.stream()
                    .filter(genre -> necessaryGenresIds.contains(genre.getId())).collect(Collectors.toList());
            book.setGenres(necessaryGenres);
        }
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("author_id", book.getAuthor().getId());

        namedParameterJdbcOperations.update("INSERT INTO books (title, author_id) " +
                        "VALUES (:title, :author_id)",
                parameterSource, keyHolder, new String[]{"id"});

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {
        var bookId = book.getId();
        var title = book.getTitle();
        var authorId = book.getAuthor().getId();
        int updateResult = jdbc.update("UPDATE books SET id = ?, title = ?, author_id = ? WHERE id = ?",
                bookId, title, authorId, bookId);
        if (updateResult < 1) {
            throw new EntityNotFoundException("Не удалось обновить записи");
        }
        //...
        // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        // Использовать метод batchUpdate
        jdbc.batchUpdate("INSERT INTO books_genres (book_id, genre_id) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, book.getId());
                        ps.setLong(2, book.getGenres().get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return book.getGenres().size();
                    }
                });
    }

    private void removeGenresRelationsFor(Book book) {
        //...
        jdbc.update("DELETE FROM books_genres WHERE book_id = ?", book.getId());
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            Author author = new Author(rs.getLong("author_id"),
                    rs.getString("authors.full_name"));
            return new Book(id, title, author, Collections.emptyList());
        }
    }

    // Использовать для findById
    @SuppressWarnings("ClassCanBeRecord")
    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<Book> {

        @Override
        public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
            Book book;
            long id = 0;
            String title = null;
            Author author = null;
            List<Genre> genres = new ArrayList<>();
            while (rs.next()) {
                if (id == 0) {
                    id = rs.getLong("book_id");
                }
                if (title == null) {
                    title = rs.getString("title");
                }
                if (author == null) {
                    author = new Author(rs.getLong("author_id"), rs.getString("authors.full_name"));
                }
                genres.add(new Genre(rs.getLong("genre_id"), rs.getString("genre_name")));
            }
            book = new Book(id, title, author, genres);
            if (book.isEmpty()) {
                return null;
            } else {
                return book;
            }
        }
    }

    private record BookGenreRelation(long bookId, long genreId) {
    }
}