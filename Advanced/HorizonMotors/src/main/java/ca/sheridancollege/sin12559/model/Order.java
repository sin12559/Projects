package ca.sheridancollege.sin12559.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private long id;
    private Long vehicleId;
    private String name;
    private String card;
}
