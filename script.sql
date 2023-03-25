CREATE TABLE PRODUCTS (
  id INT AUTO_INCREMENT PRIMARY KEY,
  quantity_stock INT NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  unit ENUM('kg', 'litre', 'unité') NOT NULL
);

CREATE TABLE USERS (
   mail VARCHAR(100),
   username VARCHAR(100) NOT NULL PRIMARY KEY,
   password VARCHAR(400) NOT NULL,
   role ENUM('admin', 'user') NOT NULL
);