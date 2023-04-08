package paf.rev.pokemart.repository;

public class DBqueries {

    //ITEM QUERIES
    public static final String INSERT_NEW_ITEM ="INSERT INTO items (item_id, name_id, name, cost, description, category) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SELECT_ITEM_BY_ITEM_ID ="SELECT * FROM items WHERE item_id = ?";
    // order by should precede limit clause. might need to use limit ? , ?
    public static final String SELECT_ALL_ITEM_ID = "SELECT item_id FROM items ORDER BY item_id LIMIT ? OFFSET ?";


}

/* ITEM.DB
private int item_id;
private String name_id;
private String name;
private double cost;
private String description;
private String category;

CREATE TABLE items(
item_id INT NOT NULL,
name_id VARCHAR(50) NOT NULL,
name VARCHAR(50) NOT NULL,
cost DOUBLE(8,2) NOT NULL DEFAULT 0.00,
description VARCHAR(250),
category VARCHAR(50),
PRIMARY KEY (item_id)
);

+-------------+--------------+------+-----+---------+-------+
| Field       | Type         | Null | Key | Default | Extra |
+-------------+--------------+------+-----+---------+-------+
| item_id     | int          | NO   | PRI | NULL    |       |
| name_id     | varchar(50)  | NO   |     | NULL    |       |
| name        | varchar(50)  | NO   |     | NULL    |       |
| cost        | double(8,2)  | NO   |     | 0.00    |       |
| description | varchar(250) | YES  |     | NULL    |       |
| category    | varchar(50)  | YES  |     | NULL    |       |
+-------------+--------------+------+-----+---------+-------+
6 rows in set 
*/

/* INVENTORY.DB
CREATE TABLE inventory(
item_id INT NOT NULL,
quantity INT NOT NULL,
PRIMARY KEY(item_id),
FOREIGN KEY(item_id) REFERENCES items(item_id)
);

+----------+------+------+-----+---------+-------+
| Field    | Type | Null | Key | Default | Extra |
+----------+------+------+-----+---------+-------+
| item_id  | int  | NO   | PRI | NULL    |       |
| quantity | int  | NO   |     | NULL    |       |
+----------+------+------+-----+---------+-------+
2 rows in set
 */

 /* PURCHASES.DB
CREATE TABLE purchases(
order_id INT NOT NULL,
order_line_id INT NOT NULL,
item_id INT NOT NULL,
quantity INT NOT NULL,
)
*/

/* 
CREATE TABLE customer_orders(
customer_id INT NOT NULL;
order_id INT NOT NULL;
)
 */

/*CUSTOMERS.DB
    private int customer_id;
    private String phone;
    private String email;
    private String shippingAddress;
 */