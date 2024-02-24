package controllers;

import java.util.Date;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Item;
import models.RatingAndReview;
import models.composite_responses.RatingAndReviewResponse;
import repositories.reviewRepository.MockReviewRepository;
import repositories.reviewRepository.ReviewRepository;
import repositories.userRepository.MockUserRepository;
import repositories.userRepository.UserRepository;
import services.RatingAndReviewService;
import services.UserService;
import views.customerViews.ReviewPage;

public class ReviewController {

    private static ReviewController instance;
    private RatingAndReviewService ratingAndReviewService;
    private UserService userService;

    public ReviewController(boolean isMock) {
        if(isMock){
            this.userService = new UserService(new MockUserRepository());
            this.ratingAndReviewService = new RatingAndReviewService(new MockReviewRepository(), userService);
        }
        else {
            this.userService = new UserService(new UserRepository());
            this.ratingAndReviewService = new RatingAndReviewService(new ReviewRepository(), userService);
        }
    }

    public static ReviewController getInstance(boolean isMock) {
        if (instance == null) {
            instance = new ReviewController(isMock);
        }
        return instance;
    }

    public void viewReviews(Item item, Stage stage, Scene scene, int userId) {
        new ReviewPage(stage, item.getId(), scene, userId);
        return;
    }

    public EventHandler<ActionEvent> submitReview(TextField rating, TextArea review, Stage stage, int itemId, int userId, Scene scene) {

			return new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

                    int ratingValue = rating.getText().isEmpty() ? 0 : Integer.parseInt(rating.getText());
                    String reviewText = review.getText();
					
                    
                    RatingAndReview newReview = new RatingAndReview(
                        itemId,
                        userId,
                        ratingValue,
                        reviewText,
                        new Date().getTime() + ""
                    );

					boolean isValid = ratingAndReviewService.addRatingAndReview(newReview);

					if(!isValid) {
						return;
					}

					PauseTransition delay = new PauseTransition(Duration.seconds(0.3));

					delay.setOnFinished(e -> {
						stage.setScene(scene);
					});

					delay.play();
				}       
			};

		}

    public List<RatingAndReviewResponse> getAllReviewsByItemId(int itemId) {
        return ratingAndReviewService.getAllRatingAndReviewByItemId(itemId);
    }
    
}