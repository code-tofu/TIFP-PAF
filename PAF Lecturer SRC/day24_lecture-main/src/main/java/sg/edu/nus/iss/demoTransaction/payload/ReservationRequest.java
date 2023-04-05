package sg.edu.nus.iss.demoTransaction.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.nus.iss.demoTransaction.model.Book;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    private List<Book> books;

    private String fullName;
}
