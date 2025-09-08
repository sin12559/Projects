package ca.sheridancollege.sin12559.model;

public class Order {
  private long id;
  private Long vehicleId;
  private String name;
  private String card; // demo only

  public Order() {}

  public Order(long id, Long vehicleId, String name, String card) {
    this.id = id;
    this.vehicleId = vehicleId;
    this.name = name;
    this.card = card;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Long getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(Long vehicleId) {
    this.vehicleId = vehicleId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCard() {
    return card;
  }

  public void setCard(String card) {
    this.card = card;
  }

  public String getMaskedCard() {
    if (card == null || card.length() < 4) return "****";
    String last4 = card.substring(card.length() - 4);
    return "**** **** **** " + last4;
  }
}
