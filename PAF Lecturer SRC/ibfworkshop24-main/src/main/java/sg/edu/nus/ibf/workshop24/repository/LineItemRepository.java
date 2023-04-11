package sg.edu.nus.ibf.workshop24.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.ibf.workshop24.model.FruitProducts;
import sg.edu.nus.ibf.workshop24.model.LineItem;
import sg.edu.nus.ibf.workshop24.service.OrderUtility;

import static sg.edu.nus.ibf.workshop24.repository.DBQueries.*;

@Repository
public class LineItemRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public static BigDecimal discount; 

    public List<FruitProducts> getProducts() {
        String query = GET_ALL_PRODUCTS;

        return jdbcTemplate.query(query, (rs, rownum) -> {
            FruitProducts fruitProducts = new FruitProducts();
            fruitProducts.setId(rs.getInt("id"));
            fruitProducts.setName(rs.getString("name"));
            fruitProducts.setStandardPrice(rs.getBigDecimal("standard_price"));
            fruitProducts.setDiscount(rs.getBigDecimal("discount"));
            return fruitProducts;
        });
    }

    public void addLineItems(List<LineItem> lineItems, String orderId) {
        List<Object[]> data = lineItems.stream()
                .map(li -> {
                    Object[] l = new Object[5];
                    l[0] = li.getProduct();
                    l[1] = OrderUtility.calculateUnitPrice(li.getProduct(), li.getQuantity());
                    l[2] = discount;
                    l[3] = li.getQuantity();
                    l[4] = orderId;
                    return l;
                })
                .toList();

        // product, unit_price, discount, quantity, order_id
        jdbcTemplate.batchUpdate(INSERT_PURCHASE_ORDER_DETAILS, data);
    }

}
