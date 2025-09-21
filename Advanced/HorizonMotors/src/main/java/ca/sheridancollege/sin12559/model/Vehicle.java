package ca.sheridancollege.sin12559.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class Vehicle {
    private String id;
    private String make;
    private String model;
    private int year;
    private int mileage;   // km
    private int price;     // CAD dollars (0 = POA)
    private String img;    // /img/cars/xxx.jpg
    private String trim;
    private String fuel;   // Gas/Hybrid/EV
    private String drive;  // AWD/FWD/RWD

    // NEW:
    private boolean featured;   // show on /featured
    private String status;      // AVAILABLE, SOLD, COMING_SOON
    private String body;        // Coupe, SUV, Roadster, etc.
}
