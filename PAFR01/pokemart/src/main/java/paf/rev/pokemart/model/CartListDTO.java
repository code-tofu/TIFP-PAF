package paf.rev.pokemart.model;

import java.util.List;

public class CartListDTO {

    private List<QuantityDTO> cartList;

    public CartListDTO() {
    }


    public CartListDTO(List<QuantityDTO> cartList) {
        this.cartList = cartList;
    }

    public List<QuantityDTO> getCartList() {
        return cartList;
    }

    public void setCartList(List<QuantityDTO> cartList) {
        this.cartList = cartList;
    }

    @Override
    public String toString() {
        return "CartList [cartList=" + cartList + "]";
    }
    
}
