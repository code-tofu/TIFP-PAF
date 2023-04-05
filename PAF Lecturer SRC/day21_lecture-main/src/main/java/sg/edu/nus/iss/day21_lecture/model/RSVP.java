package sg.edu.nus.iss.day21_lecture.model;

import java.sql.Date;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RSVP {
    @Id
    private Integer Id;
    
    @NotNull
    @Size(min = 10, max = 150, message="Full name must be between 10 and 150 characters")
    private String fullName;

    @Email(message="Email must not be blank and must be invalid format")
    @Size(max = 150, message="Email only accepts a max of 150 characters")
    private String email;

    private String phone;

    private Date confirmationDate;

    private String comments;
}
