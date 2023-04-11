package sg.edu.nus.ibf.workshop24.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import sg.edu.nus.ibf.workshop24.exception.OrderException;
import sg.edu.nus.ibf.workshop24.model.LineItem;
import sg.edu.nus.ibf.workshop24.model.PurchaseOrder;

@Controller
@RequestMapping(path = "/order")
public class CartController {

    @PostMapping(consumes = "application/x-www-form-urlencoded")
    public String postChart(HttpServletRequest httpRequest, Model model, HttpSession session) throws OrderException{

        List<LineItem> lineItems = (List<LineItem>) session.getAttribute("cart");

        if (null == lineItems) {
            lineItems = new ArrayList<LineItem>();
            session.setAttribute("cart", lineItems);
        }

       
        String item = httpRequest.getParameter("item");

        Integer quantity = Integer.parseInt(httpRequest.getParameter("quantity"));

        lineItems.add(new LineItem(item, quantity));

        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseOrder.setLineItems(lineItems);

        session.setAttribute("checkoutCart", purchaseOrder);
        model.addAttribute("lineItems", lineItems);

        return "cart_template";

    }

}
