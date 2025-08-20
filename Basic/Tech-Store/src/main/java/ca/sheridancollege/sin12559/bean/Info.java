package ca.sheridancollege.sin12559.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    private String itemName;
    private String storeName;
    private double price;
    private String description;
    private String category;

}
