package sg.edu.nus.iss.day21b_lecture.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer id;

    // firstName (Entity) --> first_name (mySQL)
    // firstname (entity) --> firstname (mySQL)
    private String firstName;

    private String lastName;

    private Integer salary;

    private List<Dependant> dependants; 
}
