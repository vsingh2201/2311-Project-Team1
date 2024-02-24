package models.composite_responses;

public class RatingAndReviewResponse {

    private int reviewId;
    private int itemId;
    private int customerId;
    private int rating ; // 1-5
    private String review;
    private String date;
    private String userFirstName;
    private String userLastName;

    public RatingAndReviewResponse(int reviewId, int itemId, int customerId, int rating, String review, String date, String userFirstName, String userLastName) {
        this.reviewId = reviewId;
        this.itemId = itemId;
        this.customerId = customerId;
        this.rating = rating;
        this.review = review;
        this.date = date;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
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

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }    
}

