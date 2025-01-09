CREATE TABLE IF NOT EXISTS car(
    car_id SERIAL PRIMARY KEY,
    car_name VARCHAR(50) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(10) NOT NULL,
    release_year NUMERIC NOT NULL,
    motor VARCHAR(5),
    kilometers REAL,
    price REAL NOT NULL
);