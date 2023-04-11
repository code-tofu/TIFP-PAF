package sg.edu.nus.ibf.workshop24.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import sg.edu.nus.ibf.workshop24.exception.OrderException;
import sg.edu.nus.ibf.workshop24.model.LineItem;
import sg.edu.nus.ibf.workshop24.model.PurchaseOrder;
import sg.edu.nus.ibf.workshop24.service.OrderService;

@Controller
@RequestMapping(path = "/checkout")
public class CheckoutController {

    OrderService orderService;

    CheckoutController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public String postCheckout(@RequestParam("name") String name, @RequestParam("ship_address") String ship_address,
            @RequestParam("notes") String notes, Model model, HttpSession session) throws OrderException {

        List<LineItem> lineItems = (List<LineItem>) session.getAttribute("cart");

        PurchaseOrder purchaseOrder = (PurchaseOrder) session.getAttribute("checkoutCart");

        purchaseOrder.setName(name);
        purchaseOrder.setShip_address(ship_address);
        purchaseOrder.setNotes(notes);
        orderService.createOrder(purchaseOrder);
        session.invalidate();        
        model.addAttribute("total", lineItems.size());
        return "checkout";
    }

@ExceptionHandler(OrderException.class)
    public String handleOrderException(OrderException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
