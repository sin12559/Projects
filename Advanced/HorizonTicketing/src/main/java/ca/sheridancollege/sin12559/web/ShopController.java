package ca.sheridancollege.sin12559.web;

import ca.sheridancollege.sin12559.data.InMemoryStore;
import ca.sheridancollege.sin12559.model.Vehicle;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ShopController {

  @GetMapping("/inventory")
  public String inventory(Model model) {
    model.addAttribute("vehicles", InMemoryStore.vehicles());
    return "inventory";
  }

  @GetMapping("/vehicle/{id}")
  public String vehicle(@PathVariable Long id, Model model) {
    Vehicle v =
        InMemoryStore.byId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    model.addAttribute("v", v);
    return "vehicle";
  }

  @GetMapping("/purchase")
  public String purchaseForm(@RequestParam Long vehicleId, Model model) {
    Vehicle v =
        InMemoryStore.byId(vehicleId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    model.addAttribute("v", v);
    model.addAttribute("vehicleId", vehicleId);
    return "purchase";
  }

  @PostMapping("/purchase")
  public String submitPurchase(
      @RequestParam Long vehicleId, @RequestParam String name, @RequestParam String card) {
    InMemoryStore.addOrder(vehicleId, name, card);
    return "redirect:/orders";
  }

  @GetMapping("/orders")
  public String orders(Model model) {
    model.addAttribute("orders", InMemoryStore.orders());
    model.addAttribute(
        "nameBy",
        (java.util.function.Function<Long, String>)
            id -> InMemoryStore.byId(id).map(Vehicle::fullName).orElse("-"));
    return "orders";
  }
}
