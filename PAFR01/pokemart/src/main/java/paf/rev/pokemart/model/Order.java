package paf.rev.pokemart.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import paf.rev.pokemart.repository.OrderRepo;
import paf.rev.pokemart.service.CartService;

public class Order {

    @Autowired @Qualifier("Order")
    OrderRepo orderRepo;

    @Autowired
    CartService cartSvc;

    enum PayMethod{
        CREDIT,
        PAYNOW,
        ONDELIVERY
    }
    public static Order.PayMethod[] allPaymentMethods(){
        return PayMethod.values(); //return string array instead?
    }

    private PayMethod paymentMethod;

    private LocalDateTime orderDateTime;
    //OrderID get latest/findlast id from database 
    private int order_id;
    //Customer information can be from session or from DB
    private int customer_id;
    //item_ID,Qty //Decription, Price will be pulled from DB;
    List<QuantityDTO> orderItems; 
    //calculated methods
    private double subtotal; //from cartItems
    private double discount; //from promocode
    private double tax; //8%
    private double shippingFee; //express or not
    private double total; //sum total


    public Order createOrder(int customer_id, ArrayList<QuantityDTO> cart, Map<String,String> cart_details){
        Order newOrder = new Order();
        LocalDateTime now = LocalDateTime.now();
        newOrder.setOrderDateTime(now);
        
        newOrder.setCustomer_id(customer_id);
        newOrder.setOrderItems(cart);
        
        Map<String,Double> cost = cartSvc.calculateTotal(cart, cart_details);
        newOrder.setSubtotal(cost.get("subtotal"));
        newOrder.setDiscount(cost.get("discount"));
        newOrder.setTax(cost.get("tax"));
        newOrder.setShippingFee(cost.get("shippingFee"));
        newOrder.setTotal(cost.get("total"));
        return newOrder;
    }

    //GETTERS AND SETTERS
    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }
    public void setOrderDateTime(LocalDateTime orderDate) {
        this.orderDateTime = orderDate;
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
    public List<QuantityDTO> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<QuantityDTO> orderItems) {
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
    public PayMethod getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PayMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    



    
}
