insert into authors(full_name)
values ('Stephen King'), ('J.K. Rowling'), ('Haruki Murakami');

insert into genres(name)
values ('Fantasy'), ('Science fiction'), ('Action'), ('Mystery'), ('Horror'), ('Thriller'), ('Short story');

insert into books(title, author_id)
values ('Rage', 1), ('The Shining', 1), ('Harry Potter', 2), ('Kafka on the Shore', 3), ('A wild sheep chase', 3),
('Norwegian wood', 3);

insert into books_genres(book_id, genre_id)
values (1, 5),   (1, 4),
       (2, 5),   (2, 4),  (2, 6),
       (3, 1),   (3, 4),
       (4, 6),
       (5, 6), (5, 7),
       (6, 7), (6, 3);

insert into comments(text, book_id)
values ('Very interesting book', 1), ('Could have been better', 2), ('Not recommended', 3), ('Very interesting book', 2);

insert into roles(role)
values ('admin'), ('user');

insert into users(username, password, role_id)
values ('administrator', '$2a$12$vyQbXf09gUFseffgZoux/uK0RNRI43MYtGxCFw/UMT9BH0WUVIOu6', 1),
('user', '$2a$10$28wGUwLfZck025R1H2fRWe/HIXsMdKnGNl7.3Ut4WsrhqJDmfOkv6', 2);

insert into users_roles(user_id, role_id)
values (1, 1), (2, 2);

