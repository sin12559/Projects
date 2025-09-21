package ca.sheridancollege;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PurchaseController {

    @GetMapping("/purchase")
    public String purchase(Model model,
                           @RequestParam(value="vehicleId", required=false) String vehicleId,
                           @RequestParam(value="price", required=false) String price) {
        // Optional params to prefill UI
        model.addAttribute("selected", vehicleId);
        model.addAttribute("price", price);
        return "purchase"; // -> src/main/resources/templates/purchase.html
    }

    @PostMapping("/purchase")
    public String submit(@RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String phone,
                         @RequestParam(required=false) String vehicle,
                         @RequestParam(required=false) String payment,
                         @RequestParam(required=false) String notes,
                         Model model) {
        // TODO: save reservation
        model.addAttribute("name", name);
        model.addAttribute("vehicle", vehicle);
        return "redirect:/purchase/confirmation";
    }

    @GetMapping("/purchase/confirmation")
    public String confirmation(Model model) {
        return "purchase-confirmation"; // -> templates/purchase-confirmation.html
    }
}
