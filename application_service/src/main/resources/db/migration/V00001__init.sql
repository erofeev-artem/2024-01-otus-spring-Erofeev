CREATE TABLE IF NOT EXISTS allowed_address (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    house VARCHAR(255) NOT NULL,
    building VARCHAR(255),
    structure VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS connection_address (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    allowed_address_id BIGINT NOT NULL,
    apartment VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (allowed_address_id) REFERENCES allowed_address (id)
);

INSERT INTO allowed_address(city, street, house, structure, created_at, modified_at)
VALUES ('Новосибирск', 'Кирова', '1', 'А', now(), now()),
('Новосибирск', 'Кирова', '1', 'Б', now(), now());

INSERT INTO allowed_address(city, street, house, created_at, modified_at)
VALUES ('Новосибирск', 'Кирова', '2', now(), now()),
('Новосибирск', 'Кирова', '3', now(), now()),
('Новосибирск', 'Кирова', '4', now(), now()),
('Новосибирск', 'Кирова', '5', now(), now()),
('Новосибирск', 'Кирова', '6', now(), now()),
('Новосибирск', 'Кирова', '7', now(), now()),
('Новосибирск', 'Кирова', '8', now(), now()),
('Новосибирск', 'Кирова', '9', now(), now()),
('Новосибирск', 'Кирова', '10', now(), now()),
('Новосибирск', 'Кирова', '11', now(), now()),
('Новосибирск', 'Ленина', '1', now(), now()),
('Новосибирск', 'Ленина', '2', now(), now()),
('Новосибирск', 'Ленина', '3', now(), now()),
('Новосибирск', 'Ленина', '4', now(), now()),
('Новосибирск', 'Ленина', '5', now(), now());

INSERT INTO connection_address(allowed_address_id, apartment, created_at, modified_at)
VALUES (1, '1', now(), now()), (1, '2', now(), now()), (3, '1', now(), now()),
(3, '2', now(), now()),(3, '3', now(), now());


