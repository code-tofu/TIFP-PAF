package paf.rev.pokemart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import paf.rev.pokemart.model.QuantityDTO;
import paf.rev.pokemart.repository.ItemRepo;

@Service
public class CartService {

    private static final double GST = 0.08;
    private static final double EXPRESS_SHIPPING_FEE = 2.0;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired @Qualifier("Item")
    ItemRepo itemRepo;

    public Map<String,Double> calculateTotal(ArrayList<QuantityDTO> cart, Map<String,String> cart_details){
        Map<String,Double> cart_cost = new HashMap<>();

        double subtotal = 0.0;
        for(QuantityDTO item: cart){
            Optional<Double> item_cost = itemRepo.getItemCostbyId(item.getItem_id());
            if(item_cost.isEmpty()){
                continue; //TODO: Need to throw some kind of error, although unlikely.
            }
            subtotal += (item_cost.get() * item.getItem_qty());
        }
        cart_cost.put("subtotal", subtotal);

        double discount = 0.0;
        //TODO: Add Voucher Feature
        if(false){
            String promocode = cart_details.get("promocode");
            //check voucher from DB and modify discount
        } 
        cart_cost.put("discount", discount);

        double shippingFee = 0.0;
        if(cart_details.get("shipping").equalsIgnoreCase("express")){ //express or standard
            shippingFee = EXPRESS_SHIPPING_FEE;
        }
        cart_cost.put("shippingFee",shippingFee);
        double tax = subtotal * GST;
        cart_cost.put("tax",tax);

        double total = subtotal - discount + tax + shippingFee;
        cart_cost.put("total",total);

        return cart_cost;
    }
    
    public ArrayList<QuantityDTO> createEmptyCartList(int size){
        ArrayList<QuantityDTO> cartList = new ArrayList<>();
        for(int i=0; i<size;i++){
            cartList.add(new QuantityDTO(0));
        }
        return cartList;
    }
}
