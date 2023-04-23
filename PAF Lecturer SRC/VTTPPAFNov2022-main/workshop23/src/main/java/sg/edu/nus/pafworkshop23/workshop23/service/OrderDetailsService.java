package sg.edu.nus.pafworkshop23.workshop23.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.pafworkshop23.workshop23.models.OrderDetails;
import sg.edu.nus.pafworkshop23.workshop23.repository.OrderRepository;

@Service
public class OrderDetailsService {
    @Autowired
    private OrderRepository odSvc;

    public OrderDetails getDiscountedOrderDetails(Integer orderId) {
        return odSvc.getDiscountedOrderDetails(orderId);
    }
}
