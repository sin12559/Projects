package ca.sheridancollege.sin12559.web;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

  public record Car(
      String id,
      String make,
      String model,
      int year,
      String price,
      String mileage,
      String transmission,
      String imageUrl) {}

  private static List<Car> inventory() {
    return List.of(
        new Car(
            "c1",
            "Toyota",
            "GR Supra",
            2023,
            "$62,990",
            "12,300 km",
            "Automatic",
            "/img/cars/supra.jpg"),
        new Car(
            "c2",
            "BMW",
            "M3 Competition",
            2022,
            "$78,500",
            "18,900 km",
            "Automatic",
            "/img/cars/m3.jpg"),
        new Car(
            "c3",
            "Audi",
            "RS5 Coupe",
            2021,
            "$69,900",
            "24,100 km",
            "Automatic",
            "/img/cars/rs5.jpg"),
        new Car(
            "c4",
            "Honda",
            "Civic Type R",
            2024,
            "$57,250",
            "3,200 km",
            "Manual",
            "/img/cars/typeR.jpg"),
        new Car(
            "c5",
            "Porsche",
            "718 Cayman",
            2020,
            "$83,700",
            "28,800 km",
            "PDK",
            "/img/cars/cayman.jpg"),
        new Car(
            "c6",
            "Ford",
            "Mustang GT",
            2021,
            "$52,400",
            "20,500 km",
            "Manual",
            "/img/cars/mustang.jpg"),
        new Car(
            "c7",
            "Chevrolet",
            "Corvette Stingray",
            2023,
            "$97,300",
            "7,800 km",
            "Automatic",
            "/img/cars/corvette.jpg"),
        new Car(
            "c8",
            "Mercedes-Benz",
            "AMG C63",
            2020,
            "$74,600",
            "32,100 km",
            "Automatic",
            "/img/cars/c63_front2.jpg"),
        new Car(
            "c9",
            "Nissan",
            "GT-R Premium",
            2019,
            "$110,000",
            "41,500 km",
            "Dual-Clutch",
            "/img/cars/gtr_front2.jpg"),
        new Car(
            "c10",
            "Lexus",
            "RC F",
            2022,
            "$79,200",
            "14,600 km",
            "Automatic",
            "/img/cars/rcf_front2.jpg"));
  }

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("pageTitle", "Horizon Motors");
    model.addAttribute("featured", inventory().subList(0, Math.min(4, inventory().size())));
    return "index";
  }

  @GetMapping("/cars")
  public String cars(
      @RequestParam(value = "q", required = false) String q,
      @RequestParam(value = "sort", required = false, defaultValue = "new") String sort,
      Model model) {

    var list = inventory();

    if (q != null && !q.isBlank()) {
      String qq = q.toLowerCase(Locale.ROOT);
      list =
          list.stream()
              .filter(
                  c ->
                      (c.make + " " + c.model).toLowerCase(Locale.ROOT).contains(qq)
                          || String.valueOf(c.year).contains(qq))
              .toList();
    }

    list =
        switch (sort) {
          case "priceAsc" ->
              list.stream().sorted(Comparator.comparingInt(HomeController::priceAsInt)).toList();
          case "priceDesc" ->
              list.stream()
                  .sorted(Comparator.comparingInt(HomeController::priceAsInt).reversed())
                  .toList();
          case "mileageAsc" ->
              list.stream().sorted(Comparator.comparingInt(HomeController::mileageAsInt)).toList();
          default -> list;
        };

    model.addAttribute("pageTitle", "Browse Cars");
    model.addAttribute("q", q);
    model.addAttribute("sort", sort);
    model.addAttribute("cars", list);
    return "cars/list";
  }

  @GetMapping("/financing")
  public String financing(
      @RequestParam(value = "price", required = false) Integer price,
      @RequestParam(value = "rate", required = false) Double rate,
      @RequestParam(value = "months", required = false) Integer months,
      Model model) {
    model.addAttribute("pageTitle", "Financing");

    if (price != null && rate != null && months != null && price > 0 && months > 0 && rate >= 0) {
      double r = rate / 100.0 / 12.0;
      double pmt =
          (r == 0) ? (price / (double) months) : (price * r) / (1 - Math.pow(1 + r, -months));
      model.addAttribute("payment", String.format("$%,.2f", pmt));
    }
    model.addAttribute("price", price);
    model.addAttribute("rate", rate);
    model.addAttribute("months", months);
    return "financing";
  }

  @GetMapping("/contact")
  public String contact(Model model) {
    model.addAttribute("pageTitle", "Contact");
    return "contact";
  }

  @PostMapping("/contact")
  public String contactSubmit(
      @RequestParam String name,
      @RequestParam String email,
      @RequestParam(required = false) String phone,
      @RequestParam String message,
      Model model) {
    model.addAttribute("sent", true);
    model.addAttribute("pageTitle", "Contact");
    return "contact";
  }

  private static int priceAsInt(Car c) {
    return Integer.parseInt(c.price.replaceAll("[^0-9]", ""));
  }

  private static int mileageAsInt(Car c) {
    return Integer.parseInt(c.mileage.replaceAll("[^0-9]", ""));
  }

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Controller
public class HomeController {

    public record Car(
        String id, String make, String model, int year,
        String price, String mileage, String transmission, String imageUrl
    ) {}

    private static List<Car> inventory() {
        return List.of(
            new Car("c1","Toyota","GR Supra",2023,"$62,990","12,300 km","Automatic","/img/cars/supra.jpg"),
            new Car("c2","BMW","M3 Competition",2022,"$78,500","18,900 km","Automatic","/img/cars/m3.jpg"),
            new Car("c3","Audi","RS5 Coupe",2021,"$69,900","24,100 km","Automatic","/img/cars/rs5.jpg"),
            new Car("c4","Honda","Civic Type R",2024,"$57,250","3,200 km","Manual","/img/cars/typeR.jpg"),
            new Car("c5","Porsche","718 Cayman",2020,"$83,700","28,800 km","PDK","/img/cars/cayman.jpg"),
            new Car("c6","Ford","Mustang GT",2021,"$52,400","20,500 km","Manual","/img/cars/mustang.jpg"),
            new Car("c7","Chevrolet","Corvette Stingray",2023,"$97,300","7,800 km","Automatic","/img/cars/corvette.jpg"),
            new Car("c8","Mercedes-Benz","AMG C63",2020,"$74,600","32,100 km","Automatic","/img/cars/c63_front2.jpg"),
            new Car("c9","Nissan","GT-R Premium",2019,"$110,000","41,500 km","Dual-Clutch","/img/cars/gtr_front2.jpg"),
            new Car("c10","Lexus","RC F",2022,"$79,200","14,600 km","Automatic","/img/cars/rcf_front2.jpg")
        );
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("pageTitle", "Horizon Motors");
        model.addAttribute("featured", inventory().subList(0, Math.min(4, inventory().size())));
        return "index";
    }

    @GetMapping("/cars")
    public String cars(
            @RequestParam(value="q", required=false) String q,
            @RequestParam(value="sort", required=false, defaultValue="new") String sort,
            Model model) {

        var list = inventory();

        if (q != null && !q.isBlank()) {
            String qq = q.toLowerCase(Locale.ROOT);
            list = list.stream().filter(c ->
                    (c.make+" "+c.model).toLowerCase(Locale.ROOT).contains(qq) ||
                    String.valueOf(c.year).contains(qq)
            ).toList();
        }

        list = switch (sort) {
            case "priceAsc"  -> list.stream().sorted(Comparator.comparingInt(HomeController::priceAsInt)).toList();
            case "priceDesc" -> list.stream().sorted(Comparator.comparingInt(HomeController::priceAsInt).reversed()).toList();
            case "mileageAsc"-> list.stream().sorted(Comparator.comparingInt(HomeController::mileageAsInt)).toList();
            default          -> list;
        };

        model.addAttribute("pageTitle","Browse Cars");
        model.addAttribute("q", q);
        model.addAttribute("sort", sort);
        model.addAttribute("cars", list);
        return "cars/list";
    }

    @GetMapping("/financing")
    public String financing(
            @RequestParam(value="price", required=false) Integer price,
            @RequestParam(value="rate", required=false) Double rate,
            @RequestParam(value="months", required=false) Integer months,
            Model model) {
        model.addAttribute("pageTitle", "Financing");

        if (price != null && rate != null && months != null && price > 0 && months > 0 && rate >= 0) {
            double r = rate / 100.0 / 12.0;
            double pmt = (r == 0) ? (price / (double)months) :
                (price * r) / (1 - Math.pow(1 + r, -months));
            model.addAttribute("payment", String.format("$%,.2f", pmt));
        }
        model.addAttribute("price", price);
        model.addAttribute("rate", rate);
        model.addAttribute("months", months);
        return "financing";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("pageTitle", "Contact");
        return "contact";
    }

    @PostMapping("/contact")
    public String contactSubmit(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(required=false) String phone,
            @RequestParam String message,
            Model model
    ) {
        model.addAttribute("sent", true);
        model.addAttribute("pageTitle", "Contact");
        return "contact";
    }

    private static int priceAsInt(Car c) {
        return Integer.parseInt(c.price.replaceAll("[^0-9]", ""));
    }
    private static int mileageAsInt(Car c) {
        return Integer.parseInt(c.mileage.replaceAll("[^0-9]", ""));
    }
}
