package sg.edu.nus.iss.demoTransaction.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    
    private Integer accountFrom;

    private Integer accountTo;

    private Float amount;
}
