package ca.sheridancollege.sin12559.Database;

import java.util.ArrayList;
import java.util.List;

public class HorizonDBMS {
    private static final List<String> data = new ArrayList<>();

    public static void add(String item) {
        data.add(item);
    }

    public static List<String> getAll() {
        return new ArrayList<>(data);
    }
}
