CREATE TABLE car_colors (
   car_id INT NOT NULL,
   color_id INT NOT NULL,
   PRIMARY KEY (car_id, color_id),
   FOREIGN KEY (car_id) REFERENCES car(id) ON DELETE CASCADE,
   FOREIGN KEY (color_id) REFERENCES color(id) ON DELETE CASCADE
);