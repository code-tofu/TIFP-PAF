package sg.edu.nus.iss.day21_lecture.model;

import lombok.Data;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Integer id;

    private String firstName;

    private String lastName;

    private Date dob;
}

