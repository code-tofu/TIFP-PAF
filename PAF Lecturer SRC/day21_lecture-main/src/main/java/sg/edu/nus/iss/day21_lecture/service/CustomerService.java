package sg.edu.nus.iss.day21_lecture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.day21_lecture.model.Customer;
import sg.edu.nus.iss.day21_lecture.model.Order;
import sg.edu.nus.iss.day21_lecture.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository custRepo;

    public List<Customer> retrieveAllCustomers() {
        return custRepo.getAllCustomers();
    }

    public List<Customer> retrieveAllCustomersWithLimitOffset(int limit, int offset) {
        return custRepo.getAllCustomersWithLimitOffset(limit, offset);
    }

    public Customer getCustomerById(int id) {
        return custRepo.getCustomerById(id);
    }

    public List<Order> getOrderByCustomerId(int customer_id) {
        return custRepo.getCustomerOrders(customer_id);
    }
}
