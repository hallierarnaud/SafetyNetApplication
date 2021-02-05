DROP TABLE IF EXISTS persons;
CREATE TABLE persons (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  address VARCHAR(250) NOT NULL,
  city VARCHAR(250) NOT NULL,
  zip VARCHAR(250) NOT NULL,
  phone VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL
);
INSERT INTO persons (first_name, last_name, adress, city, zip, phone, email) VALUES
  ('John', 'Boyd', '1509 Culver St', 'Culver', '97451', '841-874-6512', 'jaboyd@email.com'),
  ('Jacob', 'Boyd', '1509 Culver St', 'Culver', '97451', '841-874-6512', 'drk@email.com');
