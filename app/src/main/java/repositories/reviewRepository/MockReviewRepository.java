package repositories.reviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import models.RatingAndReview;

public class MockReviewRepository implements IReviewRepository{

    private static List<RatingAndReview> reviews;
    private AtomicInteger reviewIdCounter;

    public MockReviewRepository() {
        this.reviewIdCounter = new AtomicInteger(1);
        MockReviewRepository.reviews = new ArrayList<>();
    }

    public void addRatingAndReview(RatingAndReview ratingAndReview) {
        ratingAndReview.setId(reviewIdCounter.getAndIncrement());
        reviews.add(ratingAndReview);
    }

    public void updateRatingAndReview(RatingAndReview ratingAndReview) {
        reviews.stream()
            .filter(r -> r.getId() == ratingAndReview.getId())
            .forEach(r -> {
                r.setRating(ratingAndReview.getRating());
                r.setReview(ratingAndReview.getReview());
                r.setDate(ratingAndReview.getDate());
            });
    }

    public void deleteRatingAndReview(int id) {
        reviews.removeIf(r -> r.getId() == id);
    }

    public RatingAndReview getRatingAndReview(int id) {
        return reviews.stream()
            .filter(r -> r.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public RatingAndReview[] getAllRatingAndReviewByItemId(int itemId) {
        return reviews.stream()
            .filter(r -> r.getitemId() == itemId)
            .toArray(RatingAndReview[]::new);
    }

    public RatingAndReview[] getAllRatingAndReviewByCustomerId(int customerId) {
        return reviews.stream()
            .filter(r -> r.getCustomerId() == customerId)
            .toArray(RatingAndReview[]::new);
    }

    public RatingAndReview[] getAllRatingAndReviewByCustomerIdAndItemId(int customerId, int itemId) {
        return reviews.stream()
            .filter(r -> r.getCustomerId() == customerId && r.getitemId() == itemId)
            .toArray(RatingAndReview[]::new);
    }

    

    
}