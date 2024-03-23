package app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import models.RatingAndReview;
import repositories.reviewRepository.MockReviewRepository;

public class MockReviewRepositoryTest {

	private MockReviewRepository reviewRepository;

    @BeforeEach
    public void setUp() {
        reviewRepository = new MockReviewRepository();
    }

    @Test
    public void testAddRatingAndReview() {
        RatingAndReview newReview = new RatingAndReview(1, 1, 5, "Excellent product", "2024-02-24");
        reviewRepository.addRatingAndReview(newReview);
        RatingAndReview retrievedReview = reviewRepository.getRatingAndReview(1);
        assertNotNull(retrievedReview);
        assertEquals(1, retrievedReview.getitemId());
        assertEquals(1, retrievedReview.getCustomerId());
        assertEquals(5, retrievedReview.getRating());
        assertEquals("Excellent product", retrievedReview.getReview());
        assertEquals("2024-02-24", retrievedReview.getDate());
    }

    @Test
    public void testUpdateRatingAndReview() {
    	RatingAndReview newReview = new RatingAndReview(1, 1, 5, "Excellent product", "2024-02-24");
        reviewRepository.addRatingAndReview(newReview);
        RatingAndReview existingReview = reviewRepository.getRatingAndReview(1);
        existingReview.setRating(4);
        existingReview.setReview("Good product");
        existingReview.setDate("2024-02-25");
        reviewRepository.updateRatingAndReview(existingReview);
        RatingAndReview updatedReview = reviewRepository.getRatingAndReview(1);
        assertEquals(4, updatedReview.getRating());
        assertEquals("Good product", updatedReview.getReview());
        assertEquals("2024-02-25", updatedReview.getDate());
    }

    @Test
    public void testDeleteRatingAndReview() {
        reviewRepository.deleteRatingAndReview(1);
        assertNull(reviewRepository.getRatingAndReview(1));
    }

    @Test
    public void testGetRatingAndReview() {
    	RatingAndReview newReview = new RatingAndReview(1, 1, 5, "Excellent product", "2024-02-24");
        reviewRepository.addRatingAndReview(newReview);
        RatingAndReview review = reviewRepository.getRatingAndReview(1);
        assertNotNull(review);
        assertEquals(1, review.getId());
        assertEquals(1, review.getitemId());
        assertEquals(1, review.getCustomerId());
        assertEquals(5, review.getRating());
        assertEquals("Excellent product", review.getReview());
        assertEquals("2024-02-24", review.getDate());
    }

    @Test
    public void testGetAllRatingAndReviewByItemId() {
    	RatingAndReview newReview = new RatingAndReview(1, 1, 5, "Excellent product", "2024-02-24");
        reviewRepository.addRatingAndReview(newReview);
        RatingAndReview[] reviews = reviewRepository.getAllRatingAndReviewByItemId(1);
        assertEquals(1, reviews.length);
        assertEquals(1, reviews[0].getId());
        assertEquals(1, reviews[0].getitemId());
    }

    @Test
    public void testGetAllRatingAndReviewByCustomerId() {
    	RatingAndReview newReview = new RatingAndReview(1, 1, 5, "Excellent product", "2024-02-24");
        reviewRepository.addRatingAndReview(newReview);
        RatingAndReview[] reviews = reviewRepository.getAllRatingAndReviewByCustomerId(1);
        assertEquals(1, reviews.length);
        assertEquals(1, reviews[0].getId());
        assertEquals(1, reviews[0].getCustomerId());
    }

    @Test
    public void testGetAllRatingAndReviewByCustomerIdAndItemId() {
    	RatingAndReview newReview = new RatingAndReview(1, 1, 5, "Excellent product", "2024-02-24");
        reviewRepository.addRatingAndReview(newReview);
        RatingAndReview[] reviews = reviewRepository.getAllRatingAndReviewByCustomerIdAndItemId(1, 1);
        assertEquals(1, reviews.length);
        assertEquals(1, reviews[0].getId());
        assertEquals(1, reviews[0].getCustomerId());
        assertEquals(1, reviews[0].getitemId());
    }
}

    

