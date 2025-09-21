package ca.sheridancollege.sin12559;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    @GetMapping("/about")
    public String about() {
        return "about"; // looks for templates/about.html
    }
}
