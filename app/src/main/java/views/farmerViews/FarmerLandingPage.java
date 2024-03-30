package views.farmerViews;

import java.util.List;
import controllers.ItemController;
import controllers.RegistrationController;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Item;
import models.Machine;
import models.User;
import statics.DbConfig;
import utils.StringUtils;
import views.UpdateProfilePage;

import org.kordamp.bootstrapfx.BootstrapFX;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class FarmerLandingPage {
    private VBox vbox;
    private Stage stage;
    private int userId;
    private User user;

    private ItemController itemController;
    private RegistrationController registrationController;


    public FarmerLandingPage(Stage stage, User user) {
        this.itemController = ItemController.getInstance(DbConfig.IS_MOCK);
        this.registrationController = RegistrationController.getInstance(DbConfig.IS_MOCK);
        this.stage = stage;
        this.userId = user.getId();
        this.user = user;

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

        Label greeting = new Label("Hi " + user.getFirstName()+"! 👋");
        greeting.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;-fx-letter-spacing: 0.1em; -fx-text-fill: #333; -fx-font-family: 'Arial';");

        
		HBox linksContainer = new HBox(5); 
		linksContainer.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(linksContainer, javafx.scene.layout.Priority.ALWAYS);

		Hyperlink salesHistoryLink = new Hyperlink("Sales History 📜");

		salesHistoryLink.setStyle("-fx-font-size: 16px;");

		Hyperlink uploadItemLink = new Hyperlink("Upload Item 💭");

		uploadItemLink.setStyle("-fx-font-size: 16px;");
		
		linksContainer.getChildren().addAll(salesHistoryLink, uploadItemLink);
		
		// Create an ImageView for the dropdown menu
        ImageView dropdownIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/UserProfile.png")));
        dropdownIcon.setFitWidth(30);
        dropdownIcon.setFitHeight(30);
        
     // Create context menu
        ContextMenu contextMenu = new ContextMenu();

        // Create menu items
        MenuItem updateProfileItem = new MenuItem("Update Profile");
        MenuItem logoutItem = new MenuItem("Logout");

        // Add menu items to context menu
        contextMenu.getItems().addAll(updateProfileItem, logoutItem);
        
        // Add event handlers to menu items
        updateProfileItem.setOnAction(e -> {
            // Handle update profile action here
        	 Scene currentScene = stage.getScene();
        	 showUpdateProfilePage(stage,currentScene);
        });

        logoutItem.setOnAction(registrationController.onBackToLoginButtonClick(stage));
        
        // Add event handler to the ImageView to show context menu
        dropdownIcon.setOnMouseClicked(event -> {
            contextMenu.show(dropdownIcon, event.getScreenX(), event.getScreenY());
        });

        topBar.getChildren().addAll(greeting, linksContainer, dropdownIcon);
        
     

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setContent(vbox);
        scrollPane.setFitToWidth(true); 
        scrollPane.setPadding(new Insets(10));

		BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(topBar);
        mainLayout.setCenter(scrollPane); // Wrap vbox in a ScrollPane for scrolling support

        Scene scene = new Scene(mainLayout, 450, 650);
        // Add Bootstrap FX stylesheet to scene
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        
        salesHistoryLink.setOnAction(e -> showSalesHistoryPage(stage, userId,scene));
        uploadItemLink.setOnAction(e -> showUploadItemPage(stage, userId,scene));
        stage.setScene(scene);
        stage.setTitle("Farmers Hub - Farmer");
        stage.show();
    }

   private void startItemFetchLoop() {
             fetchAndDisplayItems();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), ev -> {
            fetchAndDisplayItems();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void fetchAndDisplayItems() {
        Platform.runLater(() -> {
            List<Item> fetchedItems = itemController.handleGetItemsByFarmerId(userId); 
            List<Item> items = fetchedItems != null ? FXCollections.observableArrayList(fetchedItems) : FXCollections.observableArrayList();
            vbox.getChildren().clear(); // Clear existing items

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

                Button updateButton = new Button("Update");
                Button deleteButton = new Button("Delete");
                
                // Add style class to buttons
                updateButton.getStyleClass().setAll("btn-sm","btn-success");
                deleteButton.getStyleClass().setAll("btn-sm","btn-danger");

                HBox buttonsBox = new HBox(10);
                buttonsBox.setAlignment(Pos.CENTER_RIGHT);
                buttonsBox.getChildren().addAll(updateButton, deleteButton);

                updateButton.setOnAction(e -> {
                    Scene currentScene = stage.getScene();
    
                    showUpdateItemPage(stage, item, userId, currentScene);
                });
                deleteButton.setOnAction(e -> {
                    itemController.deleteItem(item);
                    vbox.getChildren().remove(card); 
                });

                card.getChildren().addAll(itemName, itemDescription, itemPrice, itemQuantity);

                if (itemCondition != null) {
                    card.getChildren().add(itemCondition);
                }

                card.getChildren().add(buttonsBox);
                vbox.getChildren().add(card);
            }
        });
    }

    private void showSalesHistoryPage(Stage stage, int userId, Scene scene) {
        new SalesHistoryPage(stage, userId, scene);
    }

    private void showUpdateItemPage(Stage stage, Item item, int userId, Scene scene) {

        new UploadItemPage(stage, item, userId, scene);
    }

	private void showUploadItemPage(Stage stage, int userId, Scene scene) {
   
        new UploadItemPage(stage, null, userId, scene);
    }
	private void showUpdateProfilePage(Stage stage, Scene scene) {
		new UpdateProfilePage(stage,scene);
	}
}