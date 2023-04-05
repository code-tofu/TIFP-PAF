package sg.edu.nus.iss.day23_workshop.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day23_workshop.model.Order;

@Repository
public class OrderRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final static String FIND_ORDERBYID_SQL = "select o.id as order_id, o.order_date, o.customer_id,  " +
            " od.product_id, (od.quantity * od.unit_price * if(od.discount > 0, od.discount, 1)) as total_price, " +
            " (od.quantity * p.standard_cost) as cost_price " +
            " from order_details as od  " +
            " inner join products as p " +
            " on od.product_id = p.id " +
            " inner join orders as o " +
            " on o.id = od.order_id " +
            " where o.id = ?";


    public List<Order> findQrderbyId(Integer id) throws DataAccessException {

        return jdbcTemplate.query(FIND_ORDERBYID_SQL, BeanPropertyRowMapper.newInstance(Order.class), id);
    }
}
