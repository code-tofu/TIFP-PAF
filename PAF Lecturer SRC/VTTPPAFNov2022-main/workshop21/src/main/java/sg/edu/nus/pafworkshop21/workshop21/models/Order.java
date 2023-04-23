package sg.edu.nus.pafworkshop21.workshop21.models;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Order {
    private Integer id;
    private Customer c;
    private DateTime orderDate;
    private DateTime shippedDate;
    private String shipName;
    private Double shippingFee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getC() {
        return c;
    }

    public void setC(Customer c) {
        this.c = c;
    }

    public DateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(DateTime localDateTime) {
        this.orderDate = localDateTime;
    }

    public DateTime getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(DateTime localDateTime) {
        this.shippedDate = localDateTime;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public static Order create(SqlRowSet rs) {
        Customer c = new Customer();
        Order ord = new Order();
        c.setCustomerId(rs.getInt("customer_id"));
        c.setCompany("company");
        c.setLastName(rs.getString("last_name"));
        c.setFirstName(rs.getString("first_name"));
        c.setEmail(rs.getString("email_address"));
        c.setJobTitle(rs.getString("job_title"));
        c.setBusinessPhone(rs.getString("business_phone"));
        c.setHomePhone(rs.getString("home_phone"));
        c.setMobilePhone(rs.getString("mobile_phone"));
        c.setAddress(rs.getString("address"));
        c.setStateProvince(rs.getString("state_province"));
        ord.setC(c);
        ord.setId(rs.getInt("order_id"));
        ord.setOrderDate(new DateTime(
                DateTimeFormat.forPattern("dd/MM/yyyy")
                        .parseDateTime(rs.getString("order_date"))));
        if (rs.getString("shipped_date") != null)
            ord.setShippedDate(DateTimeFormat.forPattern("dd/MM/yyyy")
                    .parseDateTime(rs.getString("shipped_date")));
        ord.setShipName(rs.getString("ship_name"));
        ord.setShippingFee(rs.getDouble("shipping_fee"));
        return ord;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("order_id", getId())
                .add("order_date", getOrderDate() != null ? getOrderDate().toString() : "")
                .add("shipped_date", getShippedDate() != null ? getShippedDate().toString() : "")
                .add("ship_name", getShipName())
                .add("shipping_fee", getShippingFee())
                .add("customer_id", getC().getCustomerId().toString())
                .build();
    }
}
