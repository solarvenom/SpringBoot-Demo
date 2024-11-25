CREATE TABLE product_variants (
    id SERIAL PRIMARY KEY,
    colour CHAR(5) NOT NULL,
    size CHAR(6) NOT NULL,
    price MONEY NOT NULL,
    sku CHAR(5) NOT NULL UNIQUE,
    ean CHAR(13) NOT NULL UNIQUE,
    product_id INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

INSERT INTO product_variants (colour, size, price, sku, ean, product_id) VALUES
    ('RED', 'LARGE', 250.00, 'M-R-L', '730000000001K', 1),
    ('RED', 'MEDIUM', 200.00, 'M-R-M', '730000000002K', 1),
    ('RED', 'SMALL', 150.00, 'M-R-S', '730000000003K', 1),
    ('GREEN', 'LARGE', 250.00, 'M-G-L', '730000000004K', 1),
    ('GREEN', 'MEDIUM', 200.00, 'M-G-M', '730000000005K', 1),
    ('GREEN', 'SMALL', 150.00, 'M-G-S', '730000000006K', 1),
    ('BLUE', 'LARGE', 250.00, 'M-B-L', '730000000007K', 1),
    ('BLUE', 'MEDIUM', 200.00, 'M-B-M', '730000000008K', 1),
    ('BLUE', 'SMALL', 150.00, 'M-B-S', '730000000009K', 1),
    ('RED', 'LARGE', 950.00, 'C-R-L', '730000000010K', 2),
    ('RED', 'MEDIUM', 750.00, 'C-R-M', '730000000011K', 2),
    ('RED', 'SMALL', 550.00, 'C-R-S', '730000000012K', 2),
    ('GREEN', 'LARGE', 950.00, 'C-G-L', '730000000013K', 2),
    ('GREEN', 'MEDIUM', 750.00, 'C-G-M', '730000000014K', 2),
    ('GREEN', 'SMALL', 550.00, 'C-G-S', '730000000015K', 2),
    ('BLUE', 'LARGE', 950.00, 'C-B-L', '730000000016K', 2),
    ('BLUE', 'MEDIUM', 750.00, 'C-B-M', '730000000017K', 2),
    ('BLUE', 'SMALL', 550.00, 'C-B-S', '730000000018K', 2);