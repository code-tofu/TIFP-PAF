package sg.edu.nus.iss.day21b_lecture.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.day21b_lecture.model.Employee;
import sg.edu.nus.iss.day21b_lecture.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeService empSvc;

    @GetMapping
    public String listEmployees(Model model) {
        List<Employee> employees = empSvc.findAll();
        model.addAttribute("employees", employees);

        return "employeelist";
    }

    @GetMapping("/addnew")
    public String createEmployee(Model model) {
        Employee employeeForm = new Employee();

        model.addAttribute("employeeForm", employeeForm);
        return "newemployee";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employForm") Employee empForm, BindingResult result) {
        
        if (result.hasErrors()) {
            return "addnew";
        }

        Employee newEmployee = new Employee();
        newEmployee.setFirstName(empForm.getFirstName());
        newEmployee.setLastName(empForm.getLastName());
        newEmployee.setSalary(empForm.getSalary());
        empSvc.save(newEmployee);

        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        empSvc.delete(id);

        return "redirect:/employees";
    }

    @GetMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") Integer id, Model model) {
        Employee retrievedEmployee = empSvc.findById(id);

        model.addAttribute("employeeForm", retrievedEmployee);
        return "updateemployee";
    }
    
}
