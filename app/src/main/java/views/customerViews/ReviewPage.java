package views.customerViews;

import java.util.List;

import controllers.ItemController;
import controllers.ReviewController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.composite_responses.RatingAndReviewResponse;
import statics.DbConfig;
import utils.DateUtils;

public class ReviewPage {

    private ReviewController reviewController;
    private ItemController itemController;

    public ReviewPage(Stage stage, int itemId, Scene previousScene, int userId) {

        this.reviewController = ReviewController.getInstance(DbConfig.IS_MOCK);
        this.itemController = ItemController.getInstance(DbConfig.IS_MOCK);
        
        stage.setTitle("Farmers Hub - Customer - Reviews");
       

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(10);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> goBack(stage, previousScene));

        Label pageTitle = new Label(); 
        pageTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-letter-spacing: 0.1em; -fx-text-fill: #333; -fx-font-family: 'Arial';");


        HBox.setHgrow(pageTitle, Priority.ALWAYS);
        pageTitle.setMaxWidth(Double.MAX_VALUE);
        pageTitle.setAlignment(Pos.CENTER);

        topBar.getChildren().addAll(backButton, pageTitle);

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));
        mainLayout.getChildren().add(topBar);


        String itemName = itemController.getItemById(itemId).getName();
        pageTitle.setText("Reviews for " + itemName);


        List<RatingAndReviewResponse> reviews = reviewController.getAllReviewsByItemId(itemId);

        for (RatingAndReviewResponse review : reviews) {
            VBox reviewBox = new VBox(5);
            reviewBox.setPadding(new Insets(10));
            reviewBox.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f8f8f8;");
            
            Label reviewerName = new Label("👤 " + review.getUserFirstName() + " " + review.getUserLastName() );
            Label reviewDate = new Label("Date added 📅: " + DateUtils.convertEpochToStringWithoutHours(review.getDate()));
            Label rating = new Label("Rating ⭐: " + review.getRating() +" / 5");
            
            Label reviewText = new Label(review.getReview());
            reviewText.setWrapText(true); // Enable text wrapping
            

            reviewBox.getChildren().addAll(reviewerName, reviewDate, rating, reviewText);
            mainLayout.getChildren().add(reviewBox);
        }


        TextField ratingInput = new TextField();
        ratingInput.setPromptText("Rating (1-5)");

        ratingInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { 
                ratingInput.setText(newValue.replaceAll("[^\\d]", ""));
            } else if (!newValue.isEmpty()) {
                int rating = Integer.parseInt(newValue);
                if (rating < 1) ratingInput.setText("1");
                if (rating > 5) ratingInput.setText("5");
            }
        });

        TextArea reviewInput = new TextArea();
        reviewInput.setPromptText("Your review");
        reviewInput.setMaxHeight(100);

        Button submitReviewButton = new Button("Submit Review");

        submitReviewButton.setOnAction(reviewController.submitReview(ratingInput, reviewInput, stage, itemId, userId, previousScene));

        mainLayout.getChildren().addAll(new Label("Add Your Review:"), ratingInput, reviewInput, submitReviewButton);

        Scene scene = new Scene(mainLayout, 400, 600);
        Platform.runLater(() -> stage.setScene(scene));
    }

        // private List<RatingAndReviewResponse> getReviewsForItem(int itemId) {

        //     List<RatingAndReviewResponse> mockReviews = new ArrayList<>();

        //     mockReviews.add(new RatingAndReviewResponse(1, itemId, 1, 4, "Great product, loved it!", "2023-01-01", "John", "Doe"));

        //     mockReviews.add(new RatingAndReviewResponse(2, itemId, 2, 5, "Absolutely amazing! Remove all comments in project ejhhje  dhjdj ch ch djc hcb  cjcdbjud ", "2023-01-02", "Jane", "Doe"));

        //     return mockReviews;
        // }

    private void goBack(Stage stage, Scene previousScene) {
        stage.setScene(previousScene);
    }
    
}