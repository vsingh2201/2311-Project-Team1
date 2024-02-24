package models;

// item purchase history and item sold history can be gotten from this object
// if customerId and date ordered are the same, then it is in the same order list

public class OrderItem {

    private int id;
    private int itemId;
    private int customerId;
    private int quantity;
    private double price;
    private String dateOrdered;
    private boolean hasBeenPurchased;

    public OrderItem(int itemId, int customerId, int quantity, double price, String date) {
        this.itemId = itemId;
        this.customerId = customerId;
        this.quantity = quantity;
        this.price = price;
        this.dateOrdered = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(String dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public boolean hasBeenPurchased() {
        return hasBeenPurchased;
    }

    public void setHasBeenPurchased(boolean hasBeenPurchased) {
        this.hasBeenPurchased = hasBeenPurchased;
    }
}
