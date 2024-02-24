package controllers;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Item;
import models.Machine;
import models.Produce;
import models.User;
import repositories.itemRepository.ItemRepository ;
import repositories.itemRepository.MockItemRepository;
import repositories.userRepository.MockUserRepository;
import repositories.userRepository.UserRepository;
import services.ItemService;
import services.UserService;
import views.farmerViews.FarmerLandingPage;

public class ItemController {

    private static ItemController instance;
    private ItemService itemService;
    private UserService userService;

    public ItemController(boolean isMock) {
        if(isMock) {
            this.itemService = new ItemService(new MockItemRepository());
            this.userService = new UserService(new MockUserRepository());
        } else {
            this.itemService = new ItemService(new ItemRepository());
            this.userService = new UserService(new UserRepository());
        }
    }

    public static ItemController getInstance(boolean isMock) {
        if (instance == null) {
            instance = new ItemController(isMock);
        }
        return instance;
    }


    public List<Item> handleGetItemsByFarmerId(int farmerId) {
        List<Item> items = itemService.handleGetItemsByFarmerId(farmerId);

        return items.isEmpty() ? null :  items;
    }

    public void handleSetItemProperties(int itemId, TextField itemNameField, TextArea itemDescriptionArea, TextField itemPriceField, ChoiceBox<String> itemTypeBox, TextField quantityField, ChoiceBox<String> conditionBox) {

        Item item = itemService.handleGetItemById(itemId);


        itemNameField.setText(item.getName());
        itemDescriptionArea.setText(item.getDescription());
        itemPriceField.setText(String.valueOf(item.getPrice()));
        quantityField.setText(String.valueOf(item.getQuantityAvailable()));

        if (item instanceof Produce) {

            itemTypeBox.setValue("Produce");
        }
        else{
           
            Machine tempItem = (Machine) item;

            conditionBox.setValue(tempItem.getCondition());

            itemTypeBox.setValue("Machine");
        }
    }



    public EventHandler<ActionEvent> createItemOnButtonClick(TextField itemNameField, TextArea itemDescriptionArea,
            TextField itemPriceField, ChoiceBox<String> itemTypeBox, TextField quantityField, ChoiceBox<String> conditionBox, int farmerId, Stage stage, Text actionTarget) {

                return new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String name = itemNameField.getText();
                        String description = itemDescriptionArea.getText();
                        double price = itemPriceField.getText().isEmpty()? 0.0 : Double.parseDouble(itemPriceField.getText());
                        int quantity = quantityField.getText().isEmpty()? 0 : Integer.parseInt(quantityField.getText());
                        String type = itemTypeBox.getValue();
                        String condition = conditionBox.getValue();

                        boolean isValid = itemService.handleCreateItem(name, description, price, quantity, type, farmerId, actionTarget, condition);

                        if(!isValid) {
                            return;
                        }

		                User farmer = userService.handleGetUserById(farmerId);

                        new FarmerLandingPage(stage, farmer);
                    }
                };
    }

    public EventHandler<ActionEvent> updateItemOnButtonClick(TextField itemNameField, TextArea itemDescriptionArea,
        TextField itemPriceField, ChoiceBox<String> itemTypeBox, TextField quantityField, ChoiceBox<String> conditionBox, int farmerId, Stage stage, Text actionTarget) {

        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = itemNameField.getText();
                String description = itemDescriptionArea.getText();
                double price = Double.parseDouble(itemPriceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                String type = itemTypeBox.getValue();
                String condition = conditionBox.getValue();


                boolean isValid = itemService.handleUpdateItem(name, description, price, quantity, type, farmerId, actionTarget, condition.toUpperCase());

                if(!isValid) {
                    return;
                }

                User farmer = userService.handleGetUserById(farmerId);

                new FarmerLandingPage(stage, farmer);
            }
        };
    }
    
    public void deleteItem(Item item) {
        itemService.handleDeleteItem(item);
    }



    public List<Item> handleGetAllItems() {
        List<Item> items = itemService.handleGetAllItems();

        return items.isEmpty() ? null :  items;
    }

    public Item getItemById(int itemId) {

        Item item = itemService.handleGetItemById(itemId);

        return item;
    }
    
}