CREATE TABLE IF NOT EXISTS car(
    car_id SERIAL PRIMARY KEY,
    car_name VARCHAR(50) NOT NULL,
    model VARCHAR(10) NOT NULL,
    release_year NUMERIC NOT NULL,
    motor VARCHAR(5),
    kilometers REAL,
    price REAL NOT NULL,
    man_id INT REFERENCES manufacturer(man_id)
);

CREATE TABLE IF NOT EXISTS manufacturer (
    man_id SERIAL PRIMARY KEY,
    man_name VARCHAR(100) NOT NULL,
    country VARCHAR(50)
);