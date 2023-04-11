package sg.edu.nus.ibf.workshop24.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Component;

import sg.edu.nus.ibf.workshop24.model.FruitProducts;
import sg.edu.nus.ibf.workshop24.repository.LineItemRepository;

@Component
public class OrderUtility {

    public static Object calculateUnitPrice(String product, Integer quantity) {

        List<FruitProducts> fruitProducts = FruitProducts.fruitProducts;

        BigDecimal standardPrice = new BigDecimal(0.0);
        BigDecimal discount = new BigDecimal(0.0);
        BigDecimal one = new BigDecimal(1);

        for (FruitProducts fruit : fruitProducts) {
            if (fruit.getName().equalsIgnoreCase(product)) {
                standardPrice = fruit.getStandardPrice();
                discount = fruit.getDiscount();
                LineItemRepository.discount = discount;
            }

        }

        BigDecimal totalPrice = BigDecimal.valueOf(quantity).multiply(standardPrice);

        BigDecimal totalPriceAfterDiscount;
        if (discount.compareTo(BigDecimal.ONE) == 0) {
            totalPriceAfterDiscount = totalPrice;
        } else {
            totalPriceAfterDiscount = totalPrice.multiply(one.subtract(discount));

        }

        BigDecimal totalPriceAfterTax = totalPriceAfterDiscount
                .add(totalPriceAfterDiscount.multiply(BigDecimal.valueOf(0.05)));

               // totalPriceAfterTax = totalPriceAfterTax.set
                totalPriceAfterTax = totalPriceAfterTax.setScale(2, RoundingMode.DOWN);
                totalPriceAfterTax = totalPriceAfterTax.stripTrailingZeros();
                System.out.println("Unit Price: " +totalPriceAfterTax);
              //  totalPriceAfterTax = BigDecimal.valueOf(12.2);
        return totalPriceAfterTax;
    }

}
