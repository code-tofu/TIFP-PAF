package paf.rev.pokemart.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Login {

    @NotBlank(message="Please key in a valid email")
    @Email(message = "Please key in a valid email")
    @Size(max=30, message= "Please key in an email less than characters")
    private String email;

    // @NotBlank(message="Please key in a valid email")
    @Size(min = 8, max = 30, message = "Please key in a password between 8 and 30 characters")
    private String password;


    //GETTERS AND SETTERS
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }



    
    
    
}


