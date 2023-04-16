package paf.rev.pokemart.model;

public class QuantityDTO {

    private int item_id;
    private int item_qty;
    
    public int getItem_id() {
        return item_id;
    }
    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
    public int getItem_qty() {
        return item_qty;
    }
    public void setItem_qty(int item_qty) {
        this.item_qty = item_qty;
    }
    @Override
    public String toString() {
        return "Quantity [item_id=" + item_id + ", item_qty=" + item_qty + "]";
    }
    
    public QuantityDTO() {
    }
    public QuantityDTO(int item_id, int item_qty) {
        this.item_id = item_id;
        this.item_qty = item_qty;
    }
    public QuantityDTO(int item_qty) {
        this.item_qty = item_qty;
    }
    

    
}
