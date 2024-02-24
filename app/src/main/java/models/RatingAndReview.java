package models;

public class RatingAndReview {

    private int id;
    private int itemId;
    private int customerId;
    private int rating ; 
    private String review;
    private String date;

    public RatingAndReview(int itemId, int customerId, int rating, String review, String date) {
        this.itemId = itemId;
        this.customerId = customerId;
        this.rating = rating;
        this.review = review;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getitemId() {
        return itemId;
    }

    public void setitemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}