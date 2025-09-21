package ca.sheridancollege.sin12559;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class FinanceApiController {
  // Example: /api/payment?price=30000&apr=6.9&months=60
  @GetMapping("/api/payment")
  public Map<String,Object> payment(
      @RequestParam double price,
      @RequestParam double apr,
      @RequestParam int months) {

    double r = apr / 100.0 / 12.0;                 // monthly interest
    double pmt = (r == 0)
        ? price / months
        : (price * r) / (1 - Math.pow(1 + r, -months));

    return Map.of(
      "price", price,
      "apr", apr,
      "months", months,
      "monthlyPayment", Math.round(pmt * 100.0) / 100.0
    );
  }
}
