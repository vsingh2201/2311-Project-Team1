package models;

public class Item {

    protected int id;
    protected int farmerId;
    protected String name;
    protected String description;
    protected double price;
    protected int quantityAvailable;

    public Item(int farmerId, String name, String description, double price, int quantityAvailable){
        this.farmerId = farmerId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public int getQuantityAvailable(){
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable){
        this.quantityAvailable = quantityAvailable;
    }

    public void updateQuantityAvailable(int quantity){
        this.quantityAvailable += quantity;
    }
}