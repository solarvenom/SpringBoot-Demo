CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

INSERT INTO products (name, description) VALUES 
    ('Mug', 'A coffee/tea mug that comes in multiple colours and sizes'),
    ('Coat', 'A coat that comes in mutilple and colours and sizes');