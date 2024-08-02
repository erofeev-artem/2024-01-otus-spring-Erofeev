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

CREATE TABLE IF NOT EXISTS client_address (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    allowed_address_id BIGINT NOT NULL,
    apartment VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (allowed_address_id) REFERENCES allowed_address (id)
);

