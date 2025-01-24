CREATE TABLE Employee
(
    employee_id  SERIAL PRIMARY KEY,
    first_name   VARCHAR(50) NOT NULL,
    last_name    VARCHAR(50) NOT NULL,
    position     VARCHAR(50),
    salary       DECIMAL(10, 2),
    hire_date    DATE,
    phone_number VARCHAR(15),
    email        VARCHAR(100) UNIQUE,
--     address      VARCHAR(250)
);