package views.customerViews;

import java.util.List;

import controllers.LoginController;
import controllers.OrderController;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.User;
import models.composite_responses.OrderItemResponse;
import statics.DbConfig;
import utils.StringUtils;


public class CartPage {

        private int userId;
        private Label totalLabel;
        private OrderController orderController;
        private LoginController loginController;


        public CartPage(Stage stage, int userId, Scene previousScene) {
            this.orderController = OrderController.getInstance(DbConfig.IS_MOCK);
            this.loginController = LoginController.getInstance(DbConfig.IS_MOCK);

            this.userId = userId;


            // Top bar for back button and page title
            HBox topBar = new HBox();
            topBar.setPadding(new Insets(10, 10, 10, 10));
            topBar.setAlignment(Pos.CENTER_LEFT);
            topBar.setSpacing(20);
        
            Button backButton = new Button("Back");
            backButton.setOnAction(e -> new CustomerLandingPage(stage, loginController.getUserById(userId)));
        
            Label pageTitle = new Label("Your Cart");
            pageTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
            HBox.setHgrow(pageTitle, Priority.ALWAYS);
            pageTitle.setMaxWidth(Double.MAX_VALUE);
            pageTitle.setAlignment(Pos.CENTER);

            topBar.getChildren().addAll(backButton, pageTitle);
        
            List<OrderItemResponse> cartItems = orderController.viewCart(userId);
        
            VBox itemsContainer = new VBox(10);
            itemsContainer.setAlignment(Pos.TOP_CENTER);
            itemsContainer.setSpacing(5);
            itemsContainer.setPadding(new Insets(10));

            Label totalLabel = new Label();
            this.totalLabel = totalLabel;


        double total = 0;
        int totalQuantity = 0;

        if (cartItems.isEmpty()) {
            Label emptyCartLabel = new Label("Add items to Your Cart");
            emptyCartLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
            itemsContainer.getChildren().add(emptyCartLabel);
        } else {
            for (OrderItemResponse item : cartItems) {
                VBox card = new VBox(10);
                card.setPadding(new Insets(10));
                card.setStyle("-fx-border-color: lightgrey; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");

                Label nameLabel = new Label(item.getName());
                nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
                Label priceLabel = new Label(String.format("$%.2f", item.getPrice()));
                Label quantityLabel = new Label("Qty: ");

                // Quantity adjustment controls
                TextField quantityField = new TextField(String.valueOf(item.getQuantity()));
                quantityField.setPrefWidth(40);
                Button decreaseButton = new Button("-");
                Button increaseButton = new Button("+");
                HBox quantityAdjustmentBox = new HBox(5, decreaseButton, quantityField, increaseButton);
                quantityAdjustmentBox.setAlignment(Pos.CENTER_LEFT);

                decreaseButton.setOnAction(e -> {
                    int quantity = Integer.parseInt(quantityField.getText());
                    if (quantity > 1) {
                        quantityField.setText(String.valueOf(--quantity));
                        orderController.reduceQuantityBy1(item.getOrderItemId(), userId);
                    }
                    else if (quantity == 1) {
                        // orderController.removeFromCart(item.getOrderItemId(), userId);
                        orderController.reduceQuantityBy1(item.getOrderItemId(), userId);
                        itemsContainer.getChildren().remove(card);    
                    }
                    updateTotals(); 

                });

                increaseButton.setOnAction(e -> {
                    int quantity = Integer.parseInt(quantityField.getText());
                    
                    if(quantity < orderController.getQuantityAvailable(item.getItemId())){
                        quantityField.setText(String.valueOf(++quantity));
                        orderController.increaseQuantityBy1(item.getOrderItemId(), userId);
                    }
                    updateTotals();
                        
                });


                HBox infoBox = new HBox(10, nameLabel, priceLabel, quantityLabel, quantityAdjustmentBox);
                infoBox.setAlignment(Pos.CENTER_LEFT);
                card.getChildren().add(infoBox);

                itemsContainer.getChildren().add(card);

                total += item.getPrice() * Integer.parseInt(quantityField.getText());
                totalQuantity += Integer.parseInt(quantityField.getText());
            }
        }

        totalLabel.setText(String.format("Total Items: %d \n Total Price: $%s\n\n\n", totalQuantity, String.format("%s", StringUtils.formatNumberPrice(total))));
        totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button placeOrderButton = new Button("Place Order");
        placeOrderButton.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");

        placeOrderButton.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
        
        placeOrderButton.setOnAction(event -> {
            orderController.placeOrder(userId);
            
            User user = loginController.getUserById(userId);

            PauseTransition delay = new PauseTransition(Duration.seconds(0.3));

            delay.setOnFinished(e -> {
                stage.setScene(previousScene);
                new CustomerLandingPage(stage, user);
            });

            delay.play();
        });

        VBox mainContainer = new VBox();
        mainContainer.getChildren().addAll(topBar, itemsContainer);
        if (!cartItems.isEmpty()) {
            mainContainer.getChildren().add(totalLabel); // Only show the place order button if there are items in the cart
            mainContainer.getChildren().add(placeOrderButton); // Only show the place order button if there are items in the cart
        }
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setPadding(new Insets(20));

        Scene scene = new Scene(mainContainer, 400, 600);
        stage.setScene(scene);
        stage.setTitle("Farmers Hub - Customer - Shopping Cart");
        stage.show();
    }

    private void updateTotals() {
        double total = 0;
        int totalQuantity = 0;
    
        for (OrderItemResponse item : orderController.viewCart(userId)) {
            total += item.getPrice() * item.getQuantity();
            totalQuantity += item.getQuantity();
        }
    
        totalLabel.setText(String.format("Total Items: %d \n Total Price: $%s\n\n\n", totalQuantity, String.format("%s", StringUtils.formatNumberPrice(total))));
    }
    



}