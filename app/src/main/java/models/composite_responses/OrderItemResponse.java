package models.composite_responses;

public class OrderItemResponse {

    private int itemId;
    private int orderItemId;
    private int farmerId;
    private String name;
    private String description;
    private double price;
    private int customerId;
    private int quantity;
    private String dateOrdered;

    public OrderItemResponse(int itemId, int orderItemId, int farmerId,  String name, String description, double price, int customerId, int quantity, String dateOrdered) {
        this.itemId = itemId;
        this.orderItemId = orderItemId;
        this.farmerId = farmerId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.customerId = customerId;
        this.quantity = quantity;
        this.dateOrdered = dateOrdered;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(String dateOrdered) {
        this.dateOrdered = dateOrdered;
    }
}
