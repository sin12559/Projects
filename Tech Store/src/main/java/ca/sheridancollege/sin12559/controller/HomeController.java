package ca.sheridancollege.sin12559.controller;

import ca.sheridancollege.sin12559.bean.Info;
import ca.sheridancollege.sin12559.bean.Store;
import ca.sheridancollege.sin12559.Repository.Database;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final Database database;

    public HomeController(Database database) {
        this.database = database;
    }

    @GetMapping("/")
    public String home() {
        return "NewFile";
    }

    @GetMapping("/addStore")
    public String addStoreForm(Model model) {
        model.addAttribute("store", new Store());
        return "addStore.html";
    }

    @PostMapping("/addStore")
    public String addStore(Store store) {
        database.addStore(store);
        return "redirect:/viewStores";
    }

    @GetMapping("/viewStores")
    public String viewStores(Model model) {
        model.addAttribute("stores", database.getStores());
        return "viewStores.html";
    }

    @GetMapping("/addItem")
    public String addItemForm(Model model) {
        model.addAttribute("info", new Info());
        return "addItem.html";
    }

    @PostMapping("/addItem")
    public String addItem(Info info) {
        database.addItem(info);
        return "redirect:/viewItems";
    }

    @GetMapping("/viewItems")
    public String viewItems(Model model) {
        model.addAttribute("items", database.getItems());
        return "viewItems.html";
    }


}
