package ca.sheridancollege.sin12559;
public class VehicleOption {
    private String id; private String make; private String model; private int year; private double price;
    public VehicleOption(String id, String make, String model, int year, double price){this.id=id;this.make=make;this.model=model;this.year=year;this.price=price;}
    public String getId(){return id;} public String getMake(){return make;} public String getModel(){return model;}
    public int getYear(){return year;} public double getPrice(){return price;}
}
