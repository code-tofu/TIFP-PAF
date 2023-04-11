package sg.edu.nus.ibf.workshop24.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.ibf.workshop24.model.PurchaseOrder;
import static sg.edu.nus.ibf.workshop24.repository.DBQueries.*;

@Repository
public class PurchaseOrderRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean insertPurchaseOrder(PurchaseOrder purchaseOrder) {
        return jdbcTemplate.update(INSERT_PURCHASE_ORDER, purchaseOrder.getOrderId(), purchaseOrder.getName(),
                purchaseOrder.getShip_address(), purchaseOrder.getNotes()) > 0;
    }

}
