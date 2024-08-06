CREATE TYPE ORDER_STATUS AS ENUM
    ('NEW', 'CONFIRMED', 'COMPLETED', 'REJECTED');

CREATE TABLE IF NOT EXISTS connection_address (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    house VARCHAR(255) NOT NULL,
    building VARCHAR(255),
    structure VARCHAR(255),
    apartment VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL,
    UNIQUE (id, city, street, house, building, structure, apartment)
);

CREATE TABLE IF NOT EXISTS customer (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL
    );


CREATE TABLE IF NOT EXISTS client (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    birth_date date,
    birth_place VARCHAR(255),
    passport_series INTEGER UNIQUE NULLS NOT DISTINCT,
    passport_number INTEGER UNIQUE NULLS NOT DISTINCT,
    issued_by VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS tariff (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    speed double precision NOT NULL,
    price double precision NOT NULL,
    archived BOOLEAN,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL,
    UNIQUE (id, name, speed, price)
);

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(128),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    username VARCHAR(128),
    password VARCHAR(128),
    role_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles (id),
    UNIQUE (id, first_name, middle_name, last_name, phone_number)
);

CREATE TABLE IF NOT EXISTS orders(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    customer_id BIGINT NOT NULL,
    client_id BIGINT,
    tariff_id BIGINT NOT NULL,
    connection_address_id BIGINT NOT NULL,
    user_id BIGINT,
    order_status ORDER_STATUS NOT NULL,
    connection_date date,
    comment VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (client_id) REFERENCES client (id),
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (tariff_id) REFERENCES tariff (id),
    FOREIGN KEY (connection_address_id) REFERENCES connection_address (id)
);

INSERT INTO tariff(name, speed, price, archived, created_at, modified_at)
VALUES ('S', 50, 400.00, false, now(), now()), ('M', 100, 600.00, false, now(), now()), ('L', 200, 1000.00, false, now(), now());

INSERT INTO roles (name, created_at, modified_at)
VALUES ('Engineer', now(), now()), ('Operator', now(), now()), ('Administrator', now(), now());

INSERT INTO users (first_name, middle_name, last_name, phone_number, username, password, role_id, created_at, modified_at)
VALUES ('Андрей', 'Евгеньевич', 'Савельев', '+79243451653', 'eng1', '$2a$12$vyQbXf09gUFseffgZoux/uK0RNRI43MYtGxCFw/UMT9BH0WUVIOu6', 1, now(), now()),
 ('Егор', 'Николаевич', 'Новиков', '+79965243434', 'eng2', '$2a$12$vyQbXf09gUFseffgZoux/uK0RNRI43MYtGxCFw/UMT9BH0WUVIOu6', 1, now(), now()),
 ('Сергей', 'Владимрович','Гарипов','+79962181756','eng3', '$2a$12$vyQbXf09gUFseffgZoux/uK0RNRI43MYtGxCFw/UMT9BH0WUVIOu6', 1, now(), now()),
 ('Елена', 'Владимировна', 'Кузнецова', '+79243311918', 'op1', '$2a$12$vyQbXf09gUFseffgZoux/uK0RNRI43MYtGxCFw/UMT9BH0WUVIOu6', 2, now(), now()),
 ('Юлия', 'Николаевна', 'Борисова', '+79213311642', 'adm', '$2a$10$XWyCeRhNgJZ3BWo8q5Nuh.DthuGmAD.GfLqNiSiPIakdNi.tBT7fe', 3, now(), now());
