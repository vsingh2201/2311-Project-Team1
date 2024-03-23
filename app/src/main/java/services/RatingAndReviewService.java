package services;

import java.util.ArrayList;
import java.util.List;

import models.RatingAndReview;
import models.User;
import models.composite_responses.RatingAndReviewResponse;
import repositories.reviewRepository.IReviewRepository;

public class RatingAndReviewService {

    private IReviewRepository reviewRepository;
    private UserService userService;

    public RatingAndReviewService(IReviewRepository reviewRepository, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
    }


    public boolean addRatingAndReview(RatingAndReview ratingAndReview) {

        if(ratingAndReview.getRating() <= 0 || ratingAndReview.getRating() > 5) {
            return false;
        }

        reviewRepository.addRatingAndReview(ratingAndReview);

        return true;
    }

    public void updateRatingAndReview(RatingAndReview ratingAndReview) {
        reviewRepository.updateRatingAndReview(ratingAndReview);
    }

    public void deleteRatingAndReview(int id) {
        reviewRepository.deleteRatingAndReview(id);
    }

    public RatingAndReviewResponse getRatingAndReview(int id) {
        RatingAndReview ratingAndReview = reviewRepository.getRatingAndReview(id);
        return mapToRatingAndReviewResponse(ratingAndReview);
    }

    public List<RatingAndReviewResponse> getAllRatingAndReviewByItemId(int itemId) {
        RatingAndReview[] ratingAndReviews = reviewRepository.getAllRatingAndReviewByItemId(itemId);

        List<RatingAndReviewResponse> ratingAndReviewResponses = new ArrayList<>();

        for (RatingAndReview ratingAndReview : ratingAndReviews) {
            ratingAndReviewResponses.add(mapToRatingAndReviewResponse(ratingAndReview));
        }

        return ratingAndReviewResponses;
    }

    public RatingAndReviewResponse[] getAllRatingAndReviewByCustomerId(int customerId) {
        RatingAndReview[] ratingAndReviews = reviewRepository.getAllRatingAndReviewByCustomerId(customerId);

        RatingAndReviewResponse[] ratingAndReviewResponses = new RatingAndReviewResponse[ratingAndReviews.length];

        for (int i = 0; i < ratingAndReviews.length; i++) {
            ratingAndReviewResponses[i] = mapToRatingAndReviewResponse(ratingAndReviews[i]);
        }

        return ratingAndReviewResponses;
    }

    public RatingAndReviewResponse[] getAllRatingAndReviewByCustomerIdAndItemId(int customerId, int itemId) {
        RatingAndReview[] ratingAndReviews = reviewRepository.getAllRatingAndReviewByCustomerIdAndItemId(customerId, itemId);

        RatingAndReviewResponse[] ratingAndReviewResponses = new RatingAndReviewResponse[ratingAndReviews.length];

        for (int i = 0; i < ratingAndReviews.length; i++) {
            ratingAndReviewResponses[i] = mapToRatingAndReviewResponse(ratingAndReviews[i]);
        }

        return ratingAndReviewResponses;
    }

    private RatingAndReviewResponse mapToRatingAndReviewResponse(RatingAndReview ratingAndReview) {
        User user = userService.handleGetUserById(ratingAndReview.getCustomerId());
        return new RatingAndReviewResponse(
                ratingAndReview.getId(),
                ratingAndReview.getitemId(),
                ratingAndReview.getCustomerId(),
                ratingAndReview.getRating(),
                ratingAndReview.getReview(),
                ratingAndReview.getDate(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}