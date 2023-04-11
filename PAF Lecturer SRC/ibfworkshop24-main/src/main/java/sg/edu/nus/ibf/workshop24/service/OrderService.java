package sg.edu.nus.ibf.workshop24.service;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.ibf.workshop24.exception.OrderException;
import sg.edu.nus.ibf.workshop24.model.FruitProducts;
import sg.edu.nus.ibf.workshop24.model.PurchaseOrder;
import sg.edu.nus.ibf.workshop24.repository.LineItemRepository;
import sg.edu.nus.ibf.workshop24.repository.PurchaseOrderRepository;

@Service
public class OrderService {
    LineItemRepository itemRepository;
    PurchaseOrderRepository purchaseOrderRepository;

    OrderService(LineItemRepository itemRepository, PurchaseOrderRepository purchaseOrderRepository) {
        this.itemRepository = itemRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Transactional(rollbackFor = OrderException.class)
    public void createOrder(PurchaseOrder purchaseOrder) throws OrderException {
        // create order id
        Random rand = new Random();
        int ordId = rand.nextInt(10000000);
        String orderId = String.valueOf(ordId);

        purchaseOrder.setOrderId(ordId);
        FruitProducts.fruitProducts = itemRepository.getProducts();

        purchaseOrderRepository.insertPurchaseOrder(purchaseOrder);

        if (purchaseOrder.getLineItems().size() > 5) {
            throw new OrderException("Can not order more than 5 items");
        }
        itemRepository.addLineItems(purchaseOrder.getLineItems(), orderId);
    }

}
