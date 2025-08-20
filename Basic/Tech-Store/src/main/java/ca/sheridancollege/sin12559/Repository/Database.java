package ca.sheridancollege.sin12559.Repository;

import ca.sheridancollege.sin12559.bean.Info;
import ca.sheridancollege.sin12559.bean.Store;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

@Repository
public class Database {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Database(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void addStore(Store store) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "INSERT INTO STORE_TABLE (StoreName, Location, PhoneNumber, Category) VALUES (:storeName, :location, :phoneNumber, :category)";

        parameters.addValue("storeName", store.getStoreName());
        parameters.addValue("location", store.getLocation());
        parameters.addValue("phoneNumber", store.getPhoneNumber());
        parameters.addValue("category", store.getCategory());

        namedParameterJdbcTemplate.update(query, parameters);
    }

    public ArrayList<Store> getStores() {
        String query = "SELECT * FROM STORE_TABLE";
        List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(query, new MapSqlParameterSource());
        ArrayList<Store> stores = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Store store = new Store();
            store.setStoreName((String) row.get("StoreName"));
            store.setLocation((String) row.get("Location"));
            store.setPhoneNumber((String) row.get("PhoneNumber"));
            store.setCategory((String) row.get("Category"));
            stores.add(store);
        }
        return stores;
    }

    public void addItem(Info info) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "INSERT INTO ITEM_TABLE (ItemName, StoreName, Price, Description, Category) VALUES (:itemName, :storeName, :price, :description, :category)";

        parameters.addValue("itemName", info.getItemName());
        parameters.addValue("storeName", info.getStoreName());
        parameters.addValue("price", info.getPrice());
        parameters.addValue("description", info.getDescription());
        parameters.addValue("category", info.getCategory());

        namedParameterJdbcTemplate.update(query, parameters);
    }

    public ArrayList<Info> getItems() {
        String query = "SELECT * FROM ITEM_TABLE";
        List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(query, new MapSqlParameterSource());
        ArrayList<Info> items = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Info info = new Info();
            info.setItemName((String) row.get("ItemName"));
            info.setStoreName((String) row.get("StoreName"));
            info.setPrice(((Number) row.get("Price")).doubleValue());
            info.setDescription((String) row.get("Description"));
            info.setCategory((String) row.get("Category"));
            items.add(info);
        }
        return items;
    }

}


