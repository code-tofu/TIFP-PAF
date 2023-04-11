package sg.edu.nus.ibf.workshop24.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseOrder {
    private Integer orderId;
    private String name;
    private Date orderDate;
    private String ship_address;
    private String notes;

    public List<LineItem> lineItems = new ArrayList<LineItem>();
/* 
    public PurchaseOrder(Integer orderId, String name, Date orderDate, String ship_address, String notes,
            List<LineItem> lineItems) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.ship_address = ship_address;
        this.notes = notes;
        this.lineItems = lineItems;
    }
*/
    public PurchaseOrder() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getShip_address() {
        return ship_address;
    }

    public void setShip_address(String ship_address) {
        this.ship_address = ship_address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    @Override
    public String toString() {
        return "PurchaseOrder [orderId=" + orderId + ", name=" + name + ", orderDate=" + orderDate + ", ship_address="
                + ship_address + ", notes=" + notes + ", lineItems=" + lineItems + "]";
    }

}
