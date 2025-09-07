package ca.sheridancollege.sin12559.data;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import ca.sheridancollege.sin12559.model.Vehicle;
import ca.sheridancollege.sin12559.model.Order;

public class InMemoryStore {
  private static final List<Vehicle> VEHICLES = new ArrayList<>();
  private static final List<Order> ORDERS = new ArrayList<>();
  private static final AtomicLong ORDER_SEQ = new AtomicLong(1);

  static {
    VEHICLES.add(new Vehicle(1L, "Tesla", "Model S", "Performance EV sedan.", "https://picsum.photos/seed/tesla/600/360", "$89,990"));
    VEHICLES.add(new Vehicle(2L, "Porsche", "911 Carrera", "Iconic sports car.", "https://picsum.photos/seed/911/600/360", "$114,400"));
    VEHICLES.add(new Vehicle(3L, "Ford", "Mustang", "American muscle.", "https://picsum.photos/seed/mustang/600/360", "$34,160"));
  }

  public static List<Vehicle> vehicles() { return Collections.unmodifiableList(VEHICLES); }
  public static Optional<Vehicle> byId(Long id) {
    return VEHICLES.stream().filter(v -> Objects.equals(v.getId(), id)).findFirst();
  }

  public static synchronized Order addOrder(Long vehicleId, String name, String card) {
    long id = ORDER_SEQ.getAndIncrement();
    Order o = new Order(id, vehicleId, name, card);
    ORDERS.add(o);
    return o;
  }

  public static List<Order> orders() { return Collections.unmodifiableList(ORDERS); }
}
