CREATE TYPE ORDER_STATUS AS ENUM
    ('NEW', 'CONFIRMED', 'COMPLETED', 'REJECTED');

CREATE TABLE IF NOT EXISTS client_address (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    house VARCHAR(255) NOT NULL,
    building VARCHAR(255),
    structure VARCHAR(255),
    apartment VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS client (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
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
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS engineer (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS orders(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    client_id BIGINT NOT NULL,
    tariff_id BIGINT NOT NULL,
    client_address_id BIGINT NOT NULL,
    engineer_id BIGINT NOT NULL,
    order_status ORDER_STATUS,
    connection_date date,
    comment VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (engineer_id) REFERENCES engineer (id),
    FOREIGN KEY (client_id) REFERENCES client (id),
    FOREIGN KEY (tariff_id) REFERENCES tariff (id),
    FOREIGN KEY (client_address_id) REFERENCES client_address (id)
);

