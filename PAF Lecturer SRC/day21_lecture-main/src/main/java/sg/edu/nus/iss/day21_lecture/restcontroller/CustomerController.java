package sg.edu.nus.iss.day21_lecture.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.day21_lecture.model.Customer;
import sg.edu.nus.iss.day21_lecture.model.Order;
import sg.edu.nus.iss.day21_lecture.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    CustomerService custSvc;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return custSvc.retrieveAllCustomers();
    }

    @GetMapping("/limit")
    public List<Customer> getAllCustomers(@RequestParam("limit") int limit, @RequestParam("offset") int offset) {
        return custSvc.retrieveAllCustomersWithLimitOffset(limit, offset);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") int id) {
        return custSvc.getCustomerById(id);
    }

    @GetMapping("/{customer_id}/orders")
    public List<Order> getOrderByCustomerId(@PathVariable("customer_id") int id) {
        return custSvc.getOrderByCustomerId(id);
    }
}
