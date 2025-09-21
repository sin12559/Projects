package ca.sheridancollege.sin12559.web;

import ca.sheridancollege.sin12559.data.InMemoryStore;
import ca.sheridancollege.sin12559.model.Vehicle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final InMemoryStore store;

    public MainController(InMemoryStore store) {
        this.store = store;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/financing")
    public String financing(Model model) {
        return "financing";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact"; // templates/contact.html
    }

    @PostMapping("/contact/send")
    public String handleContact(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String message,
            Model model) {

        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("message", message);
        return "contact-success"; // templates/contact-success.html (if present)
    }

    // @GetMapping("/featured")
    public String featured(Model model) {
        List<Vehicle> featured = store.getVehicles().stream()
                .filter(Vehicle::isFeatured)
                .collect(Collectors.toList());
        model.addAttribute("featured", featured);
        return "featured";
    }
}
