package ca.sheridancollege.sin12559.model;

public class Vehicle {
  private Long id;
  private String make;
  private String model;
  private String description;
  private String image;
  private String price;

  public Vehicle() {}

  public Vehicle(
      Long id, String make, String model, String description, String image, String price) {
    this.id = id;
    this.make = make;
    this.model = model;
    this.description = description;
    this.image = image;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String fullName() {
    return (make == null ? "" : make) + " " + (model == null ? "" : model);
  }

  public Vehicle(Long id, String make, String model, String description, String image, String price) {
    this.id = id; this.make = make; this.model = model;
    this.description = description; this.image = image; this.price = price;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getMake() { return make; }
  public void setMake(String make) { this.make = make; }
  public String getModel() { return model; }
  public void setModel(String model) { this.model = model; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public String getImage() { return image; }
  public void setImage(String image) { this.image = image; }
  public String getPrice() { return price; }
  public void setPrice(String price) { this.price = price; }
  public String fullName() { return (make == null ? "" : make) + " " + (model == null ? "" : model); }
}
