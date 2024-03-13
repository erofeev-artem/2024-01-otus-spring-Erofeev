package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final GenreRepository genreRepository;

    private final JdbcOperations jdbc;

//    @Autowired
//    public JdbcBookRepository(GenreRepository genreRepository, JdbcOperations jdbc) {
//        this.genreRepository = genreRepository;
//        this.jdbc = jdbc;
//    }

    @Override
    public Optional<Book> findById(long id) {
        Book query = jdbc.query("SELECT books.id AS book_id, title, authors.full_name, authors.id AS author_id, " +
                "genres.name FROM books " +
                "LEFT JOIN books_genres ON books.id = books_genres.book_id  " +
                "LEFT JOIN genres ON books_genres.genre_id = genres.id " +
                "LEFT JOIN authors ON books.author_id = authors.id " +
                "WHERE books.id =?", new BookResultSetExtractor(), id);
        return null;
//        return Optional.ofNullable(jdbc
//                .queryForObject("SELECT id, title, author, genres FROM books WHERE id = :id",
//                        params, new JdbcBookRepository.BookRowMapper()));
//
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
//        List<BookGenreRelation> allGenreRelations = getAllGenreRelations();
//        List<BookGenreRelation> booksWithoutGenres
//                = allGenreRelations.stream().filter(gr -> gr.genreId() == 0).toList();

        return jdbc.query("SELECT books.id AS book_id, title, authors.full_name, authors.id AS author_id FROM books LEFT JOIN books_genres ON books.id = books_genres.book_id  LEFT JOIN genres ON books_genres.genre_id = genres.id  LEFT JOIN authors ON books.author_id = authors.id WHERE books_genres.genre_id IS null", new BookRowMapper());
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return jdbc.query("SELECT book_id, genre_id FROM books_genres",
                (rs, rowNum) -> new BookGenreRelation(rs.getLong("book_id"),
                        rs.getLong("genre_id")));
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {

        // Добавить книгам (booksWithoutGenres) жанры (genres) в соответствии со связями (relations)
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        //...

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {
//        jdbc.update("UPDATE books SET ")
        //...

        // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        jdbc.batchUpdate("INSERT INTO books_genres (book_id, genre_id) VALUES (?, ?)");
        // Использовать метод batchUpdate
    }

    private void removeGenresRelationsFor(Book book) {
        //...
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            Author author = new Author(rs.getLong("author_id"),rs.getString("authors.full_name"));

            return new Book();
        }
    }

    // Использовать для findById
    @SuppressWarnings("ClassCanBeRecord")
    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<Book> {

        @Override
        public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            Author author = new Author(rs.getLong("author_id"),rs.getString("author"));

//            return new Genre(id, name);
            return null;
        }
    }

    private record BookGenreRelation(long bookId, long genreId) {
    }
}
