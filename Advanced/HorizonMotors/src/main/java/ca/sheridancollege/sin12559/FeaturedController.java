package ca.sheridancollege.sin12559;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Comparator;

@Controller
public class FeaturedController {

    @GetMapping("/featured")
    public String featured(Model model) {
        List<FeaturedCar> cars = List.of(
            new FeaturedCar(
                "Ferrari Enzo",
                "2003 • 400 units",
                "6.0L V12 · 651 hp · RWD",
                "/images/featured/enzo.webp",
                "Legend of the 2000s; carbon monocoque with F1 DNA."
            ),
            new FeaturedCar(
                "BMW M1",
                "1978 • 453 units",
                "3.5L inline-6 · 277 hp · RWD",
                "/images/featured/m1.jpg",
                "BMW’s only mid-engine supercar; homologation icon."
            ),
            new FeaturedCar(
                "Koenigsegg Agera RS",
                "2015 • 25 units",
                "5.0L twin-turbo V8 · 1160+ hp · RWD",
                "/images/featured/agera.jpg",
                "Former world top-speed record; museum-grade collectible."
            ),
            new FeaturedCar(
                "Porsche 918 Spyder (Weissach)",
                "2015 • 918 units",
                "4.6L V8 hybrid · 887 hp · AWD",
                "/images/featured/918.webp",
                "Lightweight pack: magnesium wheels, added aero, lap weapon."
            ),
            new FeaturedCar(
                "Lamborghini Veneno",
                "2013 • 13 units",
                "6.5L V12 · 740 hp · AWD",
                "/images/featured/veneno.jpg",
                "Built for Lamborghini’s 50th anniversary, extreme aero."
            ),
            new FeaturedCar(
                "Bugatti Divo",
                "2020 • 40 units",
                "8.0L Quad-Turbo W16 · 1500 hp · AWD",
                "/images/featured/divo.jpg",
                "Coachbuilt handling-focused Chiron variant."
            )
        );

        // Rank so Veneno and Divo appear first
        List<FeaturedCar> ordered = new ArrayList<>(cars);
        Map<String, Integer> rank = Map.of(
            "Lamborghini Veneno", 1,
            "Bugatti Divo", 2
        );
        ordered.sort(Comparator.comparingInt(c -> rank.getOrDefault(c.getName(), 100)));

        model.addAttribute("cars", ordered);
        return "featured";
    }
}
