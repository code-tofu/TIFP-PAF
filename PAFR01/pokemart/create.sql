CREATE TABLE items(
item_id INT NOT NULL,
name_id VARCHAR(50) NOT NULL,
name VARCHAR(50) NOT NULL,
cost DOUBLE(8,2) NOT NULL DEFAULT 0.00,
description VARCHAR(250),
category VARCHAR(50),
PRIMARY KEY (item_id)
);

CREATE TABLE inventory(
item_id INT NOT NULL,
quantity INT NOT NULL,
PRIMARY KEY(item_id),
FOREIGN KEY(item_id) REFERENCES items(item_id),
CHECK (quantity>=0)
);

CREATE TABLE authentication(
email VARCHAR(30) NOT NULL,
password VARCHAR(30) NOT NULL
);