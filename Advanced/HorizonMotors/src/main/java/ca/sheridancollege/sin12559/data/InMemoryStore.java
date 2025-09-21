package ca.sheridancollege.sin12559.data;

import ca.sheridancollege.sin12559.model.Vehicle;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class InMemoryStore {
    private final List<Vehicle> vehicles = new ArrayList<>();

    @PostConstruct
    public void seed() {
        if (!vehicles.isEmpty()) return;

        // --- FEATURED EXOTICS ---
        vehicles.add(new Vehicle(u(),"Ferrari","488 GTB",2019,12000,379900,"/img/cars/gtb.jpg","Base","Gas","RWD", true,"AVAILABLE","Coupe"));
        vehicles.add(new Vehicle(u(),"Lamborghini","Huracán EVO",2020,9000,429900,"/img/cars/evo.jpg","EVO","Gas","AWD", true,"AVAILABLE","Coupe"));
        vehicles.add(new Vehicle(u(),"McLaren","720S",2018,15000,299900,"/img/cars/720s.jpg","Performance","Gas","RWD", true,"AVAILABLE","Coupe")); // 0 = POA
        vehicles.add(new Vehicle(u(),"Porsche","911 Turbo S",2021,8000,349900,"/img/cars/911.jpeg","992","Gas","AWD", true,"SOLD","Coupe"));
        vehicles.add(new Vehicle(u(),"Mercedes-AMG","G63",2022,11000,279900,"/img/cars/g63.jpg","AMG","Gas","AWD", true,"AVAILABLE","SUV"));
        vehicles.add(new Vehicle(u(),"Audi","R8 V10 Performance",2020,14000,209900,"/img/cars/r8.jpg","Performance","Gas","AWD", true,"COMING_SOON","Coupe"));

        // --- PREMIUM / FEED BROWSE ---
        vehicles.add(new Vehicle(u(),"BMW","M5 CS",2022,18000,199900,"/img/cars/m5cs.jpg","CS","Gas","AWD", false,"AVAILABLE","Sedan"));
        vehicles.add(new Vehicle(u(),"Aston Martin","Vantage",2019,22000,209900,"/img/cars/vantage.jpg","Base","Gas","RWD", false,"AVAILABLE","Coupe"));
        vehicles.add(new Vehicle(u(),"Bentley","Continental GT",2018,32000,239900,"/img/cars/contigt.jpg","W12","Gas","AWD", false,"AVAILABLE","Coupe"));
        vehicles.add(new Vehicle(u(),"Porsche","Cayenne Turbo",2020,36000,149900,"/img/cars/cayenne.jpg","Turbo","Gas","AWD", false,"AVAILABLE","SUV"));
        vehicles.add(new Vehicle(u(),"Range Rover","Autobiography",2021,28000,179900,"/img/cars/rrauto.jpg","LWB","Hybrid","AWD", false,"AVAILABLE","SUV"));
        vehicles.add(new Vehicle(u(),"Tesla","Model S Plaid",2022,24000,164900,"/img/cars/plaid.jpg","Plaid","EV","AWD", false,"AVAILABLE","Sedan"));
    }

    public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(vehicles);
    }

    private static String u(){ return UUID.randomUUID().toString(); }
}
