package sg.edu.nus.ibf.workshop24.model;

public class LineItem {

    //private Integer itemId;
    private String product;
    private Integer quantity;
    
/* 
    public LineItem(Integer itemId, Integer quantity) {
        this.product = product;
        this.itemId = itemId;
        this.quantity = quantity;
    }
*/
    public LineItem(String product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }
/* 
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
*/
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "LineItem [product=" + product + ", quantity=" + quantity + "]";
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
