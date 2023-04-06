package sg.edu.nus.IBFday23.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class OrderDetails {
    private int id;
    private DateTime orderDate;
    private Integer customerId;
    private Double totalDiscountedPrice;
    private Double totalCostPrice;

    public OrderDetails() {
    }

    public OrderDetails(int id, DateTime orderDate, Integer customerId, Double totalDiscountedPrice,
            Double totalCostPrice) {
        this.id = id;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.totalDiscountedPrice = totalDiscountedPrice;
        this.totalCostPrice = totalCostPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Double getTotalCostPrice() {
        return totalCostPrice;
    }

    public void setTotalCostPrice(Double totalCostPrice) {
        this.totalCostPrice = totalCostPrice;
    }

    @Override
    public String toString() {
        return "OrderDetails [id=" + id + ", orderDate=" + orderDate + ", customerId=" + customerId
                + ", totalDiscountedPrice=" + totalDiscountedPrice + ", totalCostPrice=" + totalCostPrice + "]";
    }

    public static OrderDetails create(SqlRowSet resulSet) {
        OrderDetails orderDetails = new OrderDetails();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime orderDate = formatter.parseDateTime(resulSet.getString("order_date"));

        orderDetails.setId(resulSet.getInt("order_id"));
        orderDetails.setOrderDate(orderDate);
        orderDetails.setCustomerId(resulSet.getInt("customer_id"));
        orderDetails.setTotalDiscountedPrice(resulSet.getDouble("discounted_price"));
        orderDetails.setTotalCostPrice(resulSet.getDouble("cost_price"));
        return orderDetails;
    }

    public JsonObject toJson() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        String orderDate = getOrderDate().toString(formatter);

        return Json.createObjectBuilder()
        .add("order_id", getId())
        .add("order_date", orderDate)
        .add("customer_id", getCustomerId())
        .add("discounted_price", getTotalDiscountedPrice().toString())
        .add("cost_price", getTotalCostPrice())
        .build();
    }

}
