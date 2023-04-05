package sg.edu.nus.iss.day21b_lecture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.day21b_lecture.model.Employee;
import sg.edu.nus.iss.day21b_lecture.repo.EmployeeRepo;

@Service
public class EmployeeService {
    
    @Autowired
    EmployeeRepo empRepo;
    
    public List<Employee> findAll() {
        return empRepo.findAll();
    }

    public Employee findById(Integer id) {
        return empRepo.findById(id);
    }

    public Boolean save(Employee employee) {
        return empRepo.save(employee);
    }

    public int update(Employee employee) {
        return empRepo.update(employee);
    }

    public int delete(Integer id) {
        return empRepo.delete(id);
    }
}
