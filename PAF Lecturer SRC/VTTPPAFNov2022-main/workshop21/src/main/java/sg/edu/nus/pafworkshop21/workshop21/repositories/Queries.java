package sg.edu.nus.pafworkshop21.workshop21.repositories;

public class Queries {
    public static final String SQL_SELECT_ALL_CUSTOMERS = "select id, company, last_name, first_name, email_address, job_title, business_phone, home_phone, mobile_phone, address, state_province from customers limit ?, ?";

    public static final String SQL_SELECT_CUSTOMERS_BY_ID = "select id, company, last_name, first_name, email_address, job_title, business_phone, home_phone, mobile_phone, address, state_province from customers where id = ?";

    public static final String SQL_SELECT_ALL_CUSTOMERS_ORDER = "select c.id as customer_id, company, c.last_name, c.first_name, c.email_address, c.job_title, c.business_phone, c.home_phone, c.mobile_phone, c.address, c.state_province, o.id as order_id, DATE_FORMAT(o.order_date, \"%d/%m/%Y\") as order_date, DATE_FORMAT(o.shipped_date, \"%d/%m/%Y\") as shipped_date, o.ship_name, o.shipping_fee from customers c, orders o where c.id = o.customer_id and o.customer_id = ?";
}
