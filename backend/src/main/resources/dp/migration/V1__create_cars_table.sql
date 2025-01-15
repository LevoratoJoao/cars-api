CREATE TABLE IF NOT EXISTS car(
    car_id SERIAL PRIMARY KEY,
    car_name VARCHAR(50) NOT NULL,
    model VARCHAR(10) NOT NULL,
    release_year NUMERIC NOT NULL,
    motor VARCHAR(5),
    kilometers REAL,
    price REAL NOT NULL,
    manufacturer_id INT,
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturer(manufacturer_id)
);

CREATE TABLE IF NOT EXISTS manufacturer (
    manufacturer_id SERIAL PRIMARY KEY,
    manufacturer_name VARCHAR(100) NOT NULL,
    country VARCHAR(50)
);