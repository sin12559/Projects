package ca.sheridancollege.sin12559.web;

import ca.sheridancollege.sin12559.data.InMemoryStore;
import ca.sheridancollege.sin12559.model.Vehicle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
public class BrowseController {
    private final InMemoryStore store;
    public BrowseController(InMemoryStore store) { this.store = store; }

    @GetMapping("/browse")
    public String browse(@RequestParam(required = false) String make,
                         @RequestParam(required = false) String q,
                         @RequestParam(required = false) Integer minPrice,
                         @RequestParam(required = false) Integer maxPrice,
                         @RequestParam(required = false) String drive,
                         @RequestParam(required = false) String fuel,
                         @RequestParam(required = false) String body,
                         @RequestParam(defaultValue = "priceDesc") String sort,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "12") int size,
                         Model model) {

        List<Vehicle> filtered = store.getVehicles().stream()
                .filter(v -> make == null || v.getMake().equalsIgnoreCase(make))
                .filter(v -> q == null || (v.getMake()+" "+v.getModel()+" "+v.getTrim())
                        .toLowerCase(Locale.ROOT).contains(q.toLowerCase(Locale.ROOT)))
                .filter(v -> minPrice == null || v.getPrice() >= minPrice)
                .filter(v -> maxPrice == null || (v.getPrice() > 0 && v.getPrice() <= maxPrice))
                .filter(v -> drive == null || drive.isBlank() || v.getDrive().equalsIgnoreCase(drive))
                .filter(v -> fuel == null || fuel.isBlank() || (v.getFuel()!=null && v.getFuel().equalsIgnoreCase(fuel)))
                .filter(v -> body == null || body.isBlank() || (v.getBody()!=null && v.getBody().equalsIgnoreCase(body)))
                .sorted(comparator(sort))
                .collect(Collectors.toList());

        int total = filtered.size();
        int totalPages = Math.max(1, (int)Math.ceil(total / (double)size));
        page = Math.max(1, Math.min(page, totalPages));
        int from = Math.min((page-1) * size, Math.max(0,total));
        int to = Math.min(from + size, total);
        List<Vehicle> pageItems = filtered.subList(from, to);

        model.addAttribute("vehicles", pageItems);
        model.addAttribute("total", total);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("sort", sort);
        model.addAttribute("make", make);
        model.addAttribute("q", q);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("drive", drive);
        model.addAttribute("fuel", fuel);
        model.addAttribute("body", body);

        return "browse";
    }

    private Comparator<Vehicle> comparator(String sort) {
        return switch (sort) {
            case "priceAsc"   -> Comparator.comparingInt(v -> v.getPrice() == 0 ? Integer.MAX_VALUE : v.getPrice());
            case "yearDesc"   -> Comparator.comparingInt(Vehicle::getYear).reversed()
                                            .thenComparingInt(v -> v.getPrice() == 0 ? Integer.MAX_VALUE : v.getPrice());
            case "mileageAsc" -> Comparator.comparingInt(Vehicle::getMileage)
                                            .thenComparingInt(v -> v.getPrice() == 0 ? Integer.MAX_VALUE : v.getPrice());
            default /* priceDesc */ -> Comparator.<Vehicle>comparingInt(v -> v.getPrice() == 0 ? Integer.MIN_VALUE : v.getPrice()).reversed();
        };
    }
}
