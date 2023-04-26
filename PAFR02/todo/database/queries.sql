CREATE USER 'fred'@'localhost' identified BY 'fred';
SELECT user FROM mysql.user;
CREATE DATABASE todo;
GRANT ALL PRIVILEGES ON todo.* TO 'fred'@'localhost';

USE users;
CREATE TABLE users(
user_id VARCHAR(8) NOT NULL,
username VARCHAR (50) NOT NULL,
name VARCHAR(50),
PRIMARY KEY (user_id)
);
DESC users;

/*  mysql -u root -p */
SHOW VARIABLES LIKE "secure_file_priv";
/* Will show a Directory. Files must be put here*/
SHOW GLOBAL VARIABLES like 'local_infile'; /* Will be OFF*/
SET GLOBAL local_infile=true;
SHOW GLOBAL VARIABLES like 'local_infile'; /* Changed to ON*/

/* mysql --local_infile=1 -u root -p */
/*note forward slash instead of backslash*/
USE todo;
LOAD DATA LOCAL INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/data.csv'
INTO TABLE users
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;
SELECT * FROM users;

/*
+----------+----------+--------+
| user_id  | username | name   |
+----------+----------+--------+
| 1b80114c | fred     | Fred   |
| 66223e28 | betty    | Betty  |
| a8b9800d | barney   | Barney |
| cf66dae3 | wilma    | Wilma  |
+----------+----------+--------+
*/