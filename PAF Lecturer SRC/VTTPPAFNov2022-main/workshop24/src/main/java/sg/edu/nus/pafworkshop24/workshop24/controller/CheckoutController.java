package sg.edu.nus.pafworkshop24.workshop24.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

//import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.pafworkshop24.workshop24.exception.OrderException;
import sg.edu.nus.pafworkshop24.workshop24.models.LineItem;
import sg.edu.nus.pafworkshop24.workshop24.models.PurchaseOrder;
import sg.edu.nus.pafworkshop24.workshop24.service.OrderService;

@Controller
@RequestMapping(path = "/checkout")
public class CheckoutController {
    @Autowired
    OrderService orderSvc;

    @PostMapping
    public String postCheckout(Model model, HttpSession sess) throws OrderException {

        List<LineItem> lineItems = (List<LineItem>) sess.getAttribute("cart");
        PurchaseOrder po = (PurchaseOrder) sess.getAttribute("checkoutCart");
        // Destroy the session
        sess.invalidate();
        orderSvc.createNewOrder(po);
        model.addAttribute("total", lineItems.size());

        return "checkout";
    }
}
