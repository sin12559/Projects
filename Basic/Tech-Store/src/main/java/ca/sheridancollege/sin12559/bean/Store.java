package ca.sheridancollege.sin12559.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    private String storeName;
    private String location;
    private String phoneNumber;
    private String category;
}
