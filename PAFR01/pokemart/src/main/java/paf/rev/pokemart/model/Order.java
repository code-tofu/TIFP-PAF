package paf.rev.pokemart.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class Order {

    enum PayMethod{
        CREDIT,
        PAYNOW,
        ONDELIVERY
    }

    //get latest order_id from DB+1
    private int order_id;
    private LocalDate orderDate;

    //Customer information can be from session or from DB
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String shippingAddress;
    private PayMethod paymentMethod;
    
    //item_ID,Qty //Decription, Price will be pulled from DB;
    Map<Integer,Integer> orderItems; 

    //calculated methods
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal tax;
    private BigDecimal shippingFee;
    private BigDecimal total;



    public static Order.PayMethod[] allPaymentMethods(){
        return PayMethod.values(); //return string array instead?
    }

    // GETTERS AND SETTERS
    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }


    public LocalDate getOrderDate() {
        return orderDate;
    }


    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }


    public String getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getCustomerPhone() {
        return customerPhone;
    }


    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }


    public String getCustomerEmail() {
        return customerEmail;
    }


    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }


    public String getShippingAddress() {
        return shippingAddress;
    }


    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }


    public Map<Integer, Integer> getOrderItems() {
        return orderItems;
    }


    public void setOrderItems(Map<Integer, Integer> orderItems) {
        this.orderItems = orderItems;
    }


    public BigDecimal getSubtotal() {
        return subtotal;
    }


    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }


    public BigDecimal getDiscount() {
        return discount;
    }


    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }


    public BigDecimal getTax() {
        return tax;
    }


    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }


    public BigDecimal getShippingFee() {
        return shippingFee;
    }


    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }


    public BigDecimal getTotal() {
        return total;
    }


    public void setTotal(BigDecimal total) {
        this.total = total;
    }


    public PayMethod getPaymentMethod() {
        return paymentMethod;
    }


    public void setPaymentMethod(PayMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    
}
