package sg.edu.nus.iss.day21b_lecture.repo;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import sg.edu.nus.iss.day21b_lecture.model.Employee;

@Repository
public class EmployeeRepo {

    RestTemplate restTemplate = new RestTemplate();

    private static final String GET_EMPLOYEES_ENDPOINT_URL = "http://localhost:8080/api/employees";
    private static final String GET_EMPLOYEEBYID_ENDPOINT_URL = "http://localhost:8080/api/employees/{id}";
    private static final String CREATE_EMPLOYEE_ENDPOINT_URL = "http://localhost:8080/api/employees";
    private static final String UPDATE_EMPLOYEE_ENDPOINT_URL = "http://localhost:8080/api/employees";
    private static final String DELETE_EMPLOYEE_ENDPOINT_URL = "http://localhost:8080/api/employees/{id}";

    public List<Employee> findAll() {

        ResponseEntity<List<Employee>> result = restTemplate.exchange(GET_EMPLOYEES_ENDPOINT_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Employee>>() {
                });
        return result.getBody();
    }

    public Employee findById(Integer id) {

        Employee employee = restTemplate.getForObject(GET_EMPLOYEEBYID_ENDPOINT_URL, Employee.class, Map.of("id", id));
        return employee;
    }

    public Boolean save(Employee employee) {

        Boolean bResult = restTemplate.postForObject(CREATE_EMPLOYEE_ENDPOINT_URL, employee, Boolean.class);
        return bResult;
    }

    public int update(Employee employee) {

        restTemplate.put(UPDATE_EMPLOYEE_ENDPOINT_URL, employee);
        return 1;
    }

    public int delete(Integer id) {

        restTemplate.delete(DELETE_EMPLOYEE_ENDPOINT_URL, Map.of("id", id));
        return 1;
    }

}
