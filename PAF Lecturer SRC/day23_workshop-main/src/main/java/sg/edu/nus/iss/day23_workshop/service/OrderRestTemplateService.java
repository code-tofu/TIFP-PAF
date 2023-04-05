package sg.edu.nus.iss.day23_workshop.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import sg.edu.nus.iss.day23_workshop.model.Order;

@Service
public class OrderRestTemplateService {

    RestTemplate restTemplate = new RestTemplate();

    private static final String GETORDERBYID_ENDPOINT_URL = "http://localhost:4000/api/orders/{order-id}";

    public List<Order> findOrderbyId(Integer orderId) {

        ResponseEntity<List<Order>> results = restTemplate.exchange(GETORDERBYID_ENDPOINT_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Order>>() {
                }, orderId);

        return results.getBody();
    }

}
