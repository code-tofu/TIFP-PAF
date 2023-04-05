package sg.edu.nus.iss.demoTransaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResvDetails {
    private Integer id;

    private Integer bookId;

    private Integer resvId;
}
