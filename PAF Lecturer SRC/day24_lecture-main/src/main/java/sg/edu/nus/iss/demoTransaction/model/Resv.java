package sg.edu.nus.iss.demoTransaction.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resv {
    private Integer id;

    private Date resvDate;

    private String fullName;
}
