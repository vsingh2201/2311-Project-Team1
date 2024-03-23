package views.customerViews;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.ItemController;
import controllers.OrderController;
import controllers.ReviewController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Item;
import models.Machine;
import models.User;
import statics.DbConfig;
import utils.StringUtils;

public class CustomerLandingPage {

	private VBox vbox; 
    private Stage stage;
    private int userId;
	private User user;
	Hyperlink goToCartLink; 
	Hyperlink orderHistoryLink;
	private Map<Integer, Integer> itemQuantities = new HashMap<>(); // Item ID to Quantity
	private OrderController orderController;
	private ItemController itemController;
	private ReviewController reviewController;

	public CustomerLandingPage(Stage stage, User user) {
        this.stage = stage;
        this.userId = user.getId();
		this.user = user;
		this.orderController = OrderController.getInstance(DbConfig.IS_MOCK);
		this.itemController = ItemController.getInstance(DbConfig.IS_MOCK);
		this.reviewController = ReviewController.getInstance(DbConfig.IS_MOCK);

        initializeUI();
        startItemFetchLoop();
    }

	private void initializeUI() {
		vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

		HBox topBar = new HBox();
		topBar.setAlignment(Pos.CENTER_LEFT);
		topBar.setPadding(new Insets(15));
		topBar.setSpacing(10);

		Label greeting = new Label("Hi " + user.getFirstName() + "! 👋");
		greeting.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;-fx-letter-spacing: 0.1em; -fx-text-fill: #333; -fx-font-family: 'Arial';");

		HBox linksContainer = new HBox(5); 
		linksContainer.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(linksContainer, javafx.scene.layout.Priority.ALWAYS); 

		orderHistoryLink = new Hyperlink("Order History 📜");

		orderHistoryLink.setStyle("-fx-font-size: 16px;");

		int cartSize = orderController.getTotalCartQuantity(userId);

		goToCartLink = new Hyperlink(cartSize == 0 ? "Cart 🛒" :"Cart" + "("+cartSize+") "+ "🛒" );
		
		
		
		goToCartLink.setStyle("-fx-font-size: 16px;");

		linksContainer.getChildren().addAll(orderHistoryLink, goToCartLink);

		topBar.getChildren().addAll(greeting, linksContainer);


		ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setContent(vbox);
        scrollPane.setFitToWidth(true); 
        scrollPane.setPadding(new Insets(10));

		BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(topBar);
        mainLayout.setCenter(scrollPane); 

        // Set the scene
        Scene scene = new Scene(mainLayout, 400, 600);
		orderHistoryLink.setOnAction(e -> showOrderHistoryPage(stage, userId, scene));
		goToCartLink.setOnAction(e -> showCartPage(stage, userId, scene));
        stage.setScene(scene);
        stage.show();

	}



	private void showOrderHistoryPage(Stage stage, int userId, Scene scene) {
		new OrderHistoryPage(stage, userId, scene);
	}

	private void showCartPage(Stage stage, int userId, Scene scene) {
		new CartPage(stage, userId, scene);
	}

	private void startItemFetchLoop() {
		fetchAndDisplayItems(); // Fetch and display items immediately
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            fetchAndDisplayItems();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

	 private void fetchAndDisplayItems() {
        Platform.runLater(() -> {
            List<Item> fetchedItems = itemController.handleGetAllItems();
            List<Item> items = fetchedItems != null ? FXCollections.observableArrayList(fetchedItems) : FXCollections.observableArrayList();
            vbox.getChildren().clear(); // Clear existing items before adding new ones

            // Rebuild the items display in vbox similar to how you initially populate it in the constructor
			for (Item item : items) {
				VBox card = new VBox(10);
				card.setPadding(new Insets(15));
				card.setStyle("-fx-border-color: lightgrey; -fx-border-radius: 5; -fx-background-radius: 5; -fx-background-color: #f9f9f9;");
	
				Label itemName = new Label("Name: " + item.getName());
				itemName.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em; -fx-letter-spacing: 0.1em; -fx-text-fill: #333; -fx-font-family: 'Arial';");
				Label itemDescription = new Label("Description: " + item.getDescription());
				Label itemPrice = new Label(String.format("Price: $%s", StringUtils.formatNumberPrice(item.getPrice())));
				Label itemQuantity = new Label("Quantity Available: " + item.getQuantityAvailable());
	
				Label itemCondition = null;
				if (item instanceof Machine) {
					Machine machine = (Machine) item;
					itemCondition = new Label("Condition: " + StringUtils.capitalize(machine.getCondition()).replace("_", " "));
				}
	
                // Quantity adjustment controls with remembered quantity
                int initialQuantity = itemQuantities.getOrDefault(item.getId(), 0); // Get the remembered quantity
                Label quantityLabel = new Label(String.valueOf(initialQuantity));

                Button decreaseButton = new Button("-");
                decreaseButton.setOnAction(e -> {
                    int quantity = Integer.parseInt(quantityLabel.getText());
                    if (quantity > 0) {
                        quantityLabel.setText(String.valueOf(--quantity));
                        itemQuantities.put(item.getId(), quantity); // Update the map with the new quantity
                    }
                });

                Button increaseButton = new Button("+");
                increaseButton.setOnAction(e -> {
                    int quantity = Integer.parseInt(quantityLabel.getText());
                    if(quantity < item.getQuantityAvailable()) {
                        quantityLabel.setText(String.valueOf(++quantity));
                        itemQuantities.put(item.getId(), quantity); // Update the map with the new quantity
                    }
                });
	
				Button addToCartButton = new Button("Add to Cart");
				Button seeReviewsButton = new Button("See Reviews");
				HBox buttonsBox = new HBox(10, addToCartButton, seeReviewsButton);
				buttonsBox.setAlignment(Pos.CENTER_RIGHT);
	
				addToCartButton.setOnAction(e -> {
					int quantity = Integer.parseInt(quantityLabel.getText()); // Get the adjusted quantity
					orderController.addToCart(item, quantity, userId);
					int cartSize = orderController.getTotalCartQuantity(userId);
					goToCartLink.setText("Cart" + "("+cartSize+") "+ "🛒");
					quantityLabel.setText("0"); 
					itemQuantities.put(item.getId(), 0); 

				});
	
				seeReviewsButton.setOnAction(e -> {
					Scene currentScene = stage.getScene();
					reviewController.viewReviews(item, stage, currentScene, userId);
				});

				HBox quantityAdjustmentBox = new HBox(5, decreaseButton, quantityLabel, increaseButton);
                quantityAdjustmentBox.setAlignment(Pos.CENTER_LEFT);
	
				HBox bottomBox = new HBox();
				bottomBox.setSpacing(10);
				Region spacer = new Region();
				HBox.setHgrow(spacer, Priority.ALWAYS); 
				bottomBox.getChildren().addAll(quantityAdjustmentBox, spacer, buttonsBox);
	
	
				card.getChildren().addAll(itemName, itemDescription, itemPrice, itemQuantity);
				if (itemCondition != null) {
					card.getChildren().add(itemCondition);
				}
	
				card.getChildren().add(bottomBox); 
				vbox.getChildren().add(card);
			}		
	
	
        });
    }
}