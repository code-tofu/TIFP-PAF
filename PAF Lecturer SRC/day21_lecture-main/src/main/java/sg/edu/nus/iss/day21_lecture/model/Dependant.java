package sg.edu.nus.iss.day21_lecture.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dependant {
    private Integer id;
    
    private String fullName;

    private String relationship;

    private Date birthDate;

    private Employee employee;
}
