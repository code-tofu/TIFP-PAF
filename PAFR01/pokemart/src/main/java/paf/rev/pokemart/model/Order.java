package paf.rev.pokemart.model;

import java.time.LocalDate;
import java.util.List;

public class Order {

    enum PayMethod{
        CREDIT,
        PAYNOW,
        ONDELIVERY
    }
    public static Order.PayMethod[] allPaymentMethods(){
        return PayMethod.values(); //return string array instead?
    }

    private LocalDate orderDate;
    //OrderID get latest/findlast id from database 
    private int order_id;
    //Customer information can be from session or from DB
    private int customer_id;
    //item_ID,Qty //Decription, Price will be pulled from DB;
    List<Quantity> orderItems; 
    //calculated methods
    private double subtotal;
    private double discount;
    private double tax;
    private double shippingFee;
    private double total;


    //GETTERS AND SETTERS
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    public int getOrder_id() {
        return order_id;
    }
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    public List<Quantity> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<Quantity> orderItems) {
        this.orderItems = orderItems;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public double getTax() {
        return tax;
    }
    public void setTax(double tax) {
        this.tax = tax;
    }
    public double getShippingFee() {
        return shippingFee;
    }
    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

/*
 CREATE TABLE orders(
    orderDate,
    customer_id,
    subtotal,
    discount,
    tax,
    shippingFee,
    total
 )
 */

    
}
