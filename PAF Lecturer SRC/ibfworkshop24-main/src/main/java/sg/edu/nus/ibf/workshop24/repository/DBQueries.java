package sg.edu.nus.ibf.workshop24.repository;

public class DBQueries {
public static final String GET_ALL_PRODUCTS = "select * from fruits_products";

public static final String INSERT_PURCHASE_ORDER = "insert into purchase_order(order_id, order_date, customer_name, ship_address, notes, tax ) values (?, SYSDATE(), ?, ?, ?, 0.05)";

public static final String INSERT_PURCHASE_ORDER_DETAILS = "insert into purchase_order_details(product, unit_price, discount, quantity, order_id) values (?,?,?,?,?)";    
}


