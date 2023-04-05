package sg.edu.nus.iss.day21_lecture.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    //id, customer_id, order_date, shipped_date, ship_name
    private Integer id;

    private Integer customerId;

    private LocalDateTime orderDate;

    private LocalDateTime shippedDate;

    private String shipName;

}