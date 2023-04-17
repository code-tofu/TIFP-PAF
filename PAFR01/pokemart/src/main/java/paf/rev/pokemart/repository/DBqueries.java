package paf.rev.pokemart.repository;

public class DBqueries {

    // ITEM REPO
    public static final String SELECT_ITEM_BY_ITEM_ID = "SELECT * FROM items WHERE item_id = ?";
    public static final String SELECT_COST_BY_ITEM_ID = "SELECT cost FROM items WHERE item_id = ?";
    public static final String SELECT_ALL_ITEM_ID = "SELECT item_id FROM items ORDER BY item_id LIMIT ? OFFSET ?";
    public static final String SELECT_ALL_INVENTORY_ITEMS = "SELECT * FROM items WHERE item_id IN (SELECT item_id FROM inventory WHERE quantity > 0) LIMIT ? OFFSET ?";
    public static final String SELECT_INVENTORY_ITEMS_BY_NAME = "SELECT * FROM items WHERE name LIKE ? AND    item_id IN (SELECT item_id FROM inventory WHERE quantity > 0)  LIMIT ? OFFSET ?";
    public static final String INSERT_NEW_ITEM = "INSERT INTO items (item_id, name_id, name, cost, description, category) VALUES (?, ?, ?, ?, ?, ?)";

    // INVENTORY REPO
    public static final String SELECT_NOSTOCK = "SELECT item_id FROM inventory WHERE quantity = 0";
    public static final String SELECT_STOCK = "SELECT item_id FROM inventory WHERE quantity > 0";
    public static final String SELECT_STOCK_BY_ITEM_ID = "SELECT quantity FROM inventory WHERE item_id = ?";
    public static final String SELECT_ALLSTOCK = "SELECT item_id, quantity FROM inventory";
    public static final String RESTOCK_INVENTORY = "UPDATE inventory SET quantity = ? WHERE quantity = 0";
    public static final String INSERT_NEWSTOCK = "INSERT INTO inventory (item_id,quantity) VALUES (?,?)";
    public static final String UPDATE_STOCK = "UPDATE inventory SET quantity = ? WHERE item_id = ?";

    //CUSTOMER REPO
    public static final String SELECT_PASSWORD_BY_EMAIL = "SELECT password FROM authentication WHERE email = ?";
    // TODO: COMPLETE ME
    // ORDER REPO
    public static final String INSERT_NEWORDER = "INSERT";
    public static final String SELECT_ORDER_BY_ORDER_ID = "SELECT";
    public static final String SELECT_ORDERS_BY_CUSTOMER_ID = "SELECT"; // select from purchases tables
    // PURCHASE REPO
    public static final String INSERT_NEWPURCHAS = "INSERT";
    public static final String SELECT_CUSTOMER_BY_ORDER_ID = "SELECT";
    public static final String SELECT_CUSTOMER_PURCHASES = "SELECT";

}

/*
 * ORDERS.TABLE
 * private PayMethod paymentMethod;
 * private LocalDate orderDate;
 * private int order_id;
 * private int customer_id;
 * List<Quantity> orderItems;
 * private double subtotal;
 * private double discount;
 * private double tax;
 * private double shippingFee;
 * private double total;
 * 
 * CREATE TABLE orders(
 * orderDate DATETIME,
 * order_id INT NOT NULL, //AUTOINCREMENT?
 * paymethod VARCHAR(20),
 * subtotal DOUBLE(16,2),
 * discount DOUBLE(16,2)
 * tax DOUBLE(16,2),
 * shippingFee DOUBLE(8,2),
 * total DOUBLE(16,2),
 * PRIMARY KEY(order_id),
 * FOREIGN KEY(order_id) REFERENCES purchases(order_id),
 * CHECK paymethod IN ('CREDIT', 'PAYNOW', 'ONDELIVERY')
 * );
 * 
 * /* CUSTOMERS.TABLE
 * private int customer_id;
 * private String name;
 * private String phone;
 * private String email;
 * private String shippingAddress;
 * 
 * CREATE TABLE customers(
 * customer_id INT NOT NULL;
 * phone VARCHAR(15);
 * email VARCHAR(30);
 * shippingAddress VARCHAR(100);
 * )
 */

/*
 * LINEITEM.DB
 * CREATE TABLE purchases(
 * order_id INT NOT NULL,
 * order_line_id INT NOT NULL,
 * item_id INT NOT NULL,
 * quantity INT NOT NULL,
 * )
 */

/*
 * CREATE TABLE PURCHASES.DB
 * customer_id INT NOT NULL;
 * order_id INT NOT NULL;
 * FOREIGN KEY(order_id) REFERENCES orders(order_id),
 * FOREIGN KEY(customer_id) REFERENCES customers(customer_id),
 * )
 */

