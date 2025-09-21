package ca.sheridancollege.sin12559;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
public class PurchaseController {

    @ModelAttribute("vehicles")
    public List<VehicleOption> vehicles() {
        return Arrays.asList(
            new VehicleOption("rs7-22", "Audi", "RS7", 2022, 142900),
            new VehicleOption("m5c-23", "BMW", "M5 Competition", 2023, 138500),
            new VehicleOption("amg-gt", "Mercedes-AMG", "GT 63 S", 2021, 154200),
            new VehicleOption("911t-24", "Porsche", "911 Turbo", 2024, 197500)
        );
    }

    @GetMapping("/purchase")
    public String purchase(Model model,
                           @RequestParam(value="vehicleId", required=false) String vehicleId,
                           @RequestParam(value="price", required=false) Double price) {
        List<VehicleOption> list = vehicles();
        if (!model.containsAttribute("vehicles")) model.addAttribute("vehicles", list);

        String selected = vehicleId != null ? vehicleId : (list.isEmpty()? null : list.get(0).getId());
        model.addAttribute("selected", selected);

        Double effectivePrice = price;
        if (effectivePrice == null && selected != null) {
            for (VehicleOption v : list) { if (Objects.equals(v.getId(), selected)) { effectivePrice = v.getPrice(); break; } }
        }
        model.addAttribute("price", effectivePrice);
        return "purchase";
    }

    @PostMapping("/purchase")
    public String submit(@RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String phone,
                         @RequestParam String vehicle,
                         @RequestParam(required=false) String payment,
                         @RequestParam(required=false) String notes,
                         Model model) {
        model.addAttribute("name", name);
        model.addAttribute("vehicle", vehicle);
        return "redirect:/purchase/confirmation";
    }

    @GetMapping("/purchase/confirmation")
    public String confirmation() { return "purchase-confirmation"; }
}
