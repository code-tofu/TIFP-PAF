package sg.edu.nus.pafworkshop23.workshop23.models;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class OrderDetails {
    private Integer id;
    private DateTime orderDate;
    private Integer customerId;
    private Double totalDiscountedPrice;
    private Double costPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(DateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Double getTotalDiscountedPrice() {
        return totalDiscountedPrice;
    }

    public void setTotalDiscountedPrice(Double totalDiscountedPrice) {
        this.totalDiscountedPrice = totalDiscountedPrice;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public static OrderDetails create(SqlRowSet rs) {
        OrderDetails od = new OrderDetails();
        od.setId(rs.getInt("order_id"));
        od.setOrderDate(new DateTime(
                DateTimeFormat.forPattern("dd/MM/yyyy")
                        .parseDateTime(rs.getString("order_date"))));
        od.setCustomerId(rs.getInt("customer_id"));
        od.setTotalDiscountedPrice(rs.getDouble("discounted_price"));
        od.setCostPrice(rs.getDouble("cost_price"));
        return od;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("order_id", getId())
                .add("order_date", getOrderDate() != null ? getOrderDate().toString() : "")
                .add("customer_id", getCustomerId())
                .add("discounted_price", getTotalDiscountedPrice().toString())
                .add("cost_price", getCostPrice().toString())
                .build();
    }

}
