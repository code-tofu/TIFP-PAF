package paf.rev.pokemart.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import paf.rev.pokemart.model.CartListDTO;
import paf.rev.pokemart.model.Item;
import paf.rev.pokemart.model.QuantityDTO;
import paf.rev.pokemart.repository.InventoryRepo;
import paf.rev.pokemart.repository.ItemRepo;
import paf.rev.pokemart.service.CartService;

@Controller 
public class CartController {

    @Autowired 
    private InventoryRepo invtRepo;

    @Autowired @Qualifier("Item")
    private ItemRepo itemRepo;

    @Autowired
    private CartService cartSvc;

    public static final int PAGE_SIZE = 10;
    
    @GetMapping("/mart/main")
    public String showMart(Model model,@RequestParam(defaultValue = "0") String page){
        try{
            int pagenum = PAGE_SIZE * Integer.parseInt(page);
            List<Item> pageItems = invtRepo.getAllItemsInInventory(PAGE_SIZE, pagenum);

            model.addAttribute("page", Integer.parseInt(page));
            model.addAttribute("martItems", pageItems);
            model.addAttribute("cartList", new CartListDTO(cartSvc.createEmptyCartList(PAGE_SIZE)));
            if(pageItems.size()<PAGE_SIZE){
                model.addAttribute("last", true);
            }

            return "mart-main";
        } catch (NumberFormatException NFErr){
            return "notfound";
        }
    }

    @GetMapping("/mart/item/{id}") 
    public String showItemDetail(Model model,@PathVariable String id){
        try{
            int itemID = Integer.parseInt(id);
            Optional<Item> itemdata = itemRepo.getItembyId(itemID);
            if(itemdata.isEmpty()){
                return "notfound";
            }

            model.addAttribute("cartItem", new QuantityDTO(1));
            model.addAttribute("item", itemdata.get());

            return "mart-detail";
        } catch(NumberFormatException numErr){
            return "notfound";
        }
    }

    @GetMapping("/mart/search")
    public String showMartSearchResults(Model model,@RequestParam(defaultValue = " ") String search, @RequestParam(defaultValue = "0") String page){
        try{
            int pagenum = PAGE_SIZE * Integer.parseInt(page);
            List<Item> pageItems = invtRepo.searchItemsInInventory("%" + search + "%" ,PAGE_SIZE, pagenum);

            model.addAttribute("page", Integer.parseInt(page));
            model.addAttribute("search", search);
            model.addAttribute("cartList", new CartListDTO(cartSvc.createEmptyCartList(PAGE_SIZE)));
            model.addAttribute("martItems", pageItems);
            if(pageItems.size()<PAGE_SIZE){
                model.addAttribute("last", true);
            }

            return "mart-main";
        } catch (NumberFormatException NFErr){
            return "notfound";
        }
    }

    @PostMapping("/cartItem")
    public String addSingleItemtoCart(@ModelAttribute QuantityDTO cartItem,HttpSession session){
        List<QuantityDTO> sessionCart = (List<QuantityDTO>)session.getAttribute("sessCart");
        if(null == sessionCart){
            sessionCart = new ArrayList<QuantityDTO>();
        }
        sessionCart.add(cartItem);
        session.setAttribute("sessCart",sessionCart);
        return "redirect:/cart/main";
    }

    @PostMapping("/cartList")
    public String addListItemstoCart(HttpSession session, @ModelAttribute CartListDTO cartList){
        List<QuantityDTO> sessionCart = (List<QuantityDTO>)session.getAttribute("sessCart");
        if(null == sessionCart){
            sessionCart = new ArrayList<QuantityDTO>();
        }
        for(QuantityDTO item: cartList.getCartList()){
            if(item.getItem_qty()!=0){
                sessionCart.add(item);

            }
        }
        session.setAttribute("sessCart",sessionCart);
        return "redirect:/cart/main";
    }

    @GetMapping("/cart/main")
    public String showCart(Model model, HttpSession session){
        List<QuantityDTO> sessionCart = (List<QuantityDTO>)session.getAttribute("sessCart");
        if(null == sessionCart){
            sessionCart = new ArrayList<QuantityDTO>();
        }
        List<Item> pageItems = new ArrayList<>();
        List<Double> itemsCost = new ArrayList<>();
        double totalCost = 0.0;
        for(QuantityDTO cartItem:sessionCart){
            Optional<Item> itemdata = itemRepo.getItembyId(cartItem.getItem_id());
            if(itemdata.isPresent()){
                pageItems.add(itemdata.get());
                itemsCost.add(itemdata.get().getCost() * cartItem.getItem_qty());
                totalCost += (itemdata.get().getCost() * cartItem.getItem_qty());
            }

        }
        model.addAttribute("itemsCost", itemsCost);
        model.addAttribute("cartItems", pageItems);
        model.addAttribute("sessCart",sessionCart);
        model.addAttribute("totalCost",totalCost);
        return "cart-main";
    }


}
