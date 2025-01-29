CREATE TABLE Sales (
    sale_id SERIAL PRIMARY KEY,
    sale_date DATE NOT NULL,
    car_id INT NOT NULL,
    customer_id INT NOT NULL,
    employee_id INT NOT NULL,
    sale_price REAL NOT NULL,
    FOREIGN KEY (car_id) REFERENCES Car(car_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id) ON DELETE CASCADE
);