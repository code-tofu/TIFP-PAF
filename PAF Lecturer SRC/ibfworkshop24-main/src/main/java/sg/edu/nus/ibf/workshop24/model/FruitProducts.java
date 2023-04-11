package sg.edu.nus.ibf.workshop24.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FruitProducts {
    private Integer id;
    private String name;
    private BigDecimal standardPrice;
    private BigDecimal discount;

    public static List<FruitProducts> fruitProducts = new ArrayList<FruitProducts>();
    

    public FruitProducts(Integer id, String name, BigDecimal standardPrice, BigDecimal discount) {
        this.id = id;
        this.name = name;
        this.standardPrice = standardPrice;
        this.discount = discount;
    }

    public FruitProducts() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "FruitProducts [id=" + id + ", name=" + name + ", standardPrice=" + standardPrice + ", discount="
                + discount + "]";
    }

}
