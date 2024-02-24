package views.farmerViews;

import java.util.List;

import controllers.OrderController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.composite_responses.OrderItemResponse;
import statics.DbConfig;
import utils.DateUtils;

public class SalesHistoryPage {

    private OrderController orderController;

   public SalesHistoryPage(Stage stage, int userId, Scene previousScene) {
        this.orderController = OrderController.getInstance(DbConfig.IS_MOCK);

        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10, 10, 10, 10));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(20);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(previousScene));

        Label pageTitle = new Label("Sales History");
        pageTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        HBox.setHgrow(pageTitle, javafx.scene.layout.Priority.ALWAYS);
        pageTitle.setMaxWidth(Double.MAX_VALUE);
        pageTitle.setAlignment(Pos.CENTER);

        topBar.getChildren().addAll(backButton, pageTitle);

        List<OrderItemResponse> orderItems = orderController.viewSalesHistory(userId);

        VBox orderList = new VBox();
        orderList.setAlignment(Pos.TOP_CENTER);
        orderList.setPadding(new Insets(10));
        orderList.setSpacing(10);

        for (OrderItemResponse item : orderItems) {
            VBox itemBox = new VBox(5);
            itemBox.setPadding(new Insets(10));
            itemBox.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");

            Label dateLabel = new Label("Date Ordered: " + DateUtils.convertEpochToString(item.getDateOrdered().toString() +""));
            Text itemName = new Text("Item: " + item.getName());
            Text quantity = new Text("Quantity Sold: " + item.getQuantity());
            Text price = new Text("Price: $" + item.getPrice());

            itemBox.getChildren().addAll(dateLabel, itemName, quantity, price);
            orderList.getChildren().add(itemBox);
        }

        // Main layout container
        VBox mainContainer = new VBox();
        mainContainer.getChildren().addAll(topBar, orderList); // Add the topBar at the top
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setPadding(new Insets(20));

        Scene scene = new Scene(mainContainer, 400, 600);
        stage.setScene(scene);
        stage.show();
}

}
    
