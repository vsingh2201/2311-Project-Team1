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
import org.kordamp.bootstrapfx.BootstrapFX;

public class SalesHistoryPage {

    private OrderController orderController;

    private static final String BACK_BUTTON_LABEL = "Back";
    private static final String SALES_HISTORY_TITLE = "Sales History";
    private static final String PAGE_TITLE_STYLE = "-fx-font-size: 20px; -fx-font-weight: bold;";
    private static final String ITEM_BOX_STYLE = "-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9;";
    private static final String DATE_ORDERED_PREFIX = "Date Ordered: ";
    private static final String ITEM_PREFIX = "Item: ";
    private static final String QUANTITY_SOLD_PREFIX = "Quantity Sold: ";
    private static final String PRICE_PREFIX = "Price: $";

    public SalesHistoryPage(Stage stage, int userId, Scene previousScene) {
        this.orderController = OrderController.getInstance(DbConfig.IS_MOCK);

        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10, 10, 10, 10));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(20);

        Button backButton = new Button(BACK_BUTTON_LABEL);
     // Set style for BackButton
        backButton.getStyleClass().setAll("btn-sm","btn-primary");
        backButton.setOnAction(e -> stage.setScene(previousScene));

        Label pageTitle = new Label(SALES_HISTORY_TITLE);
        pageTitle.setStyle(PAGE_TITLE_STYLE);

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
            itemBox.setStyle(ITEM_BOX_STYLE);

            Label dateLabel = new Label(DATE_ORDERED_PREFIX + DateUtils.convertEpochToString(item.getDateOrdered().toString() +""));
            Text itemName = new Text(ITEM_PREFIX + item.getName());
            Text quantity = new Text(QUANTITY_SOLD_PREFIX + item.getQuantity());
            Text price = new Text(PRICE_PREFIX + item.getPrice());

            itemBox.getChildren().addAll(dateLabel, itemName, quantity, price);
            orderList.getChildren().add(itemBox);
        }

        VBox mainContainer = new VBox();
        mainContainer.getChildren().addAll(topBar, orderList);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setPadding(new Insets(20));

        Scene scene = new Scene(mainContainer, 400, 600);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.setTitle("Farmers Hub - Farmer");
        stage.show();
    }

}
    
