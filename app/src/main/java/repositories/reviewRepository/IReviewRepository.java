package repositories.reviewRepository;

package repositories.reviewRepository;

import models.RatingAndReview;

public interface IReviewRepository {

    void addRatingAndReview(RatingAndReview ratingAndReview);

    void updateRatingAndReview(RatingAndReview ratingAndReview);

    void deleteRatingAndReview(int id);

    RatingAndReview getRatingAndReview(int id);

    RatingAndReview[] getAllRatingAndReviewByItemId(int itemId);

    RatingAndReview[] getAllRatingAndReviewByCustomerId(int customerId);

    RatingAndReview[] getAllRatingAndReviewByCustomerIdAndItemId(int customerId, int itemId);

} 
