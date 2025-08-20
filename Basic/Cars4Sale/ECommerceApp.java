import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ECommerceApp extends Application {
    private Stage primaryStage;
    private VBox loginRegisterPane;
    private VBox groceryListPane;
    private VBox cartPane;
    private String currentUser;
    private ListView<String> cartListView = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Grocery List App");

        createLoginRegisterPane();
        createGroceryListPane();
        createCartPane();
        showLoginRegisterPane();
    }

    private void createLoginRegisterPane() {
        Label titleLabel = new Label("Welcome to the Grocery List App");
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> handleLogin(usernameField.getText(), passwordField.getText()));
        Button registerButton = new Button("Register");
        registerButton.setOnAction(event -> handleRegistration(usernameField.getText(), passwordField.getText()));

        loginRegisterPane = new VBox(10);
        loginRegisterPane.getChildren().addAll(titleLabel, usernameLabel, usernameField, passwordLabel, passwordField,
                loginButton, registerButton);
        loginRegisterPane.setPadding(new Insets(20));
    }

    // private void createGroceryListPane() {
    // Label titleLabel = new Label("Grocery List");
    // ListView<String> groceryListView = new ListView<>();
    // TextField itemTextField = new TextField();
    // Label itemLabel = new Label("Item:");
    // Button addButton = new Button("Add");
    // addButton.setOnAction(event -> handleAddItem(groceryListView,
    // itemTextField.getText()));
    // Button editButton = new Button("Edit");
    // editButton.setOnAction(event -> handleEditItem(groceryListView,
    // itemTextField.getText()));
    // Button removeButton = new Button("Remove");
    // removeButton.setOnAction(event -> handleRemoveItem(groceryListView));

    // groceryListPane = new VBox(10);
    // groceryListPane.getChildren().addAll(titleLabel, groceryListView, itemLabel,
    // itemTextField, addButton, editButton, removeButton);
    // groceryListPane.setPadding(new Insets(20));

    private void createGroceryListPane() {

        Label titleLabel = new Label("Grocery List");
        ListView<String> groceryListView = new ListView<>();
        TextField itemTextField = new TextField();
        Label itemLabel = new Label("Item:");
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> handleAddItem(groceryListView, itemTextField.getText()));
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> handleEditItem(groceryListView, itemTextField.getText()));
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(event -> handleRemoveItem(groceryListView));

        // New button to add item to cart
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setOnAction(event -> handleAddToCart(groceryListView.getSelectionModel().getSelectedItem()));

        // New button to go to cart pane
        Button goToCartButton = new Button("Go to Cart");
        goToCartButton.setOnAction(event -> showCartPane());

        groceryListPane = new VBox(10);
        groceryListPane.getChildren().addAll(
                titleLabel, groceryListView, itemLabel, itemTextField,
                addButton, editButton, removeButton, addToCartButton, goToCartButton);
        groceryListPane.setPadding(new Insets(20));
    }

    // }

    private void createCartPane() {
        Label titleLabel = new Label("Cart");
        Button checkoutButton = new Button("Checkout");
        // You can add more buttons and functionality related to the cart here.

        cartPane = new VBox(10);
        cartPane.getChildren().addAll(titleLabel, cartListView, checkoutButton);
        cartPane.setPadding(new Insets(20));
    }

    private void showLoginRegisterPane() {
        Scene loginRegisterScene = new Scene(loginRegisterPane, 300, 250);
        primaryStage.setScene(loginRegisterScene);
        primaryStage.show();
    }

    private void showGroceryListPane() {
        Scene groceryListScene = new Scene(groceryListPane, 400, 300);
        primaryStage.setScene(groceryListScene);
    }

    private void showCartPane() {
        Scene cartScene = new Scene(cartPane, 400, 300);
        primaryStage.setScene(cartScene);
    }

    private void handleLogin(String username, String password) {
        if (isValidPassword(password)) {
            System.out.println("Logged in successfully as " + username);
            showGroceryListPane();
        } else {
            System.out.println("Login failed. Invalid password.");
            // You can show an error message or take appropriate action here.
        }
    }

    private boolean isValidPassword(String password) {
        // Check password length
        if (password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecialChar = true;
            }
        }

        // Check if password meets all criteria
        return hasUppercase && hasDigit && hasSpecialChar;
    }

    private void handleRegistration(String username, String password) {
        if (!username.isEmpty() && !password.isEmpty()) {
            if (isValidPassword(password)) {
                currentUser = username; // Store the current user
                System.out.println("Registered successfully as " + username);
                showGroceryListPane();
                showRegistrationAlert(username); // Display registration success alert
            } else {
                System.out.println("Invalid password.");
                // You can show an error message or take appropriate action here.
            }
        } else {
            System.out.println("Invalid input.");
            // You can show an error message or take appropriate action here.
        }
    }

    private void handleAddItem(ListView<String> groceryListView, String newItem) {
        if (!newItem.isEmpty()) {
            groceryListView.getItems().add(newItem);
            groceryListView.getSelectionModel().clearSelection();
        }
    }

    private void handleEditItem(ListView<String> groceryListView, String newItem) {
        int selectedIndex = groceryListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1 && !newItem.isEmpty()) {
            groceryListView.getItems().set(selectedIndex, newItem);
            groceryListView.getSelectionModel().clearSelection();
        }
    }

    private void handleRemoveItem(ListView<String> groceryListView) {
        int selectedIndex = groceryListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            groceryListView.getItems().remove(selectedIndex);
            groceryListView.getSelectionModel().clearSelection();
        }
    }

    private void handleAddToCart(String item) {
        if (item != null && !item.isEmpty()) {
            cartListView.getItems().add(item);
            System.out.println(item + " added to cart.");
        }
    }

    

    private void showCheckoutAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Checkout Successful");
        alert.setHeaderText(null);
        alert.setContentText("Your checkout was successful!");
        alert.showAndWait();
    }
    private void handleCheckout() {
        System.out.println("Checkout successful!");
        cartListView.getItems().clear(); // Clear cart after checkout
        showGroceryListPane(); // Go back to the grocery list pane
        showCheckoutAlert(); // Display the checkout alert
    }

    private void showRegistrationAlert(String username) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Registration Successful");
        alert.setHeaderText(null);
        alert.setContentText("Registration successful for user: " + username);
        alert.showAndWait();
    }

    
    


    public static void main(String[] args) {
        launch(args);
    }
}
