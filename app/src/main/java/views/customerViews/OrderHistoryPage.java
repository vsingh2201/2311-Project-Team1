package views.customerViews;

import java.util.List;

import controllers.OrderController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.composite_responses.OrderItemResponse;
import statics.DbConfig;
import utils.DateUtils;

public class OrderHistoryPage {

    private OrderController orderController;

    public OrderHistoryPage(Stage stage, int userId, Scene previousScene) {
        this.orderController = OrderController.getInstance(DbConfig.IS_MOCK);

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(10);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> goBack(stage, previousScene));

        Label pageTitle = new Label("Your Order History");
        pageTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-letter-spacing: 0.1em; -fx-text-fill: #333; -fx-font-family: 'Arial';");


        HBox.setHgrow(pageTitle, javafx.scene.layout.Priority.ALWAYS);
        pageTitle.setMaxWidth(Double.MAX_VALUE);
        pageTitle.setAlignment(Pos.CENTER);

        topBar.getChildren().addAll(backButton, pageTitle);

        VBox mainLayout = new VBox(5);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().add(topBar); 

        List<OrderItemResponse> orderItems = orderController.viewOrderHistory(userId);

        VBox orderList = new VBox();
        orderList.setPadding(new Insets(10));
        orderList.setSpacing(10);

        for (OrderItemResponse item : orderItems) {
            VBox itemBox = new VBox(5);
            itemBox.setPadding(new Insets(10));
            itemBox.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");

            Label dateLabel = new Label("Date Ordered: " + DateUtils.convertEpochToString(item.getDateOrdered().toString() +"") );
            Text itemName = new Text("Item: " + item.getName());
            Text quantity = new Text("Quantity: " + item.getQuantity());
            Text price = new Text("Price: $" + item.getPrice());

            Hyperlink addReviewLink = new Hyperlink("Add Review");
            addReviewLink.setTextFill(Color.BLUE);
            addReviewLink.setOnAction(e -> {
                new ReviewPage(stage, item.getItemId(), stage.getScene(),userId);
            });

            itemBox.getChildren().addAll(dateLabel, itemName, quantity, price, addReviewLink);
            orderList.getChildren().add(itemBox);
        }

        mainLayout.getChildren().add(orderList); 

        Scene scene = new Scene(mainLayout, 400, 600);
        stage.setScene(scene);
        stage.show();
    }

    

    private void goBack(Stage stage, Scene previousScene) {
        stage.setScene(previousScene);
    }
}