package sg.edu.nus.IBFDay21.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.IBFDay21.model.Customer;
import sg.edu.nus.IBFDay21.model.Order;

import static sg.edu.nus.IBFDay21.repository.DBQueries.*;

@Repository
public class CustomerReposaitory {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /*
     * Fetch all customers
     */
    public List<Customer> getAllCustomer(Integer offset, Integer limit) {

        List<Customer> csts = new ArrayList<Customer>();

        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_ALL_CUSTOMERS,
                offset, limit);

        while (rs.next()) {
            csts.add(Customer.create(rs));
        }

        return csts;
    }

    /*
     * Fetch customer using id
     */
    public Customer findCustomerById(Integer id) {

        List<Customer> customers = jdbcTemplate.query(SELECT_CUSTOMER_BY_ID, new CustomerRowMapper(),
                new Object[] { id });

        return customers.get(0);

    }

    /*
     * Fetch orders for customer
     * 
     */

     public List<Order> getCustomerOrders(Integer id)
     {
        List<Order> orders = new ArrayList<Order>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_ORDERS_FOR_CUSTOMERS,
        new Object[] { id });

        while (rs.next()) {
            orders.add(Order.create(rs));
        }

        return orders;
     }

}
