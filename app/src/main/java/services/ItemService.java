package services;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.text.Text;
import models.Item;
import models.Machine;
import models.Produce;
import repositories.itemRepository.IItemRepository;
import statics.ItemStatics;

public class ItemService {
    
    private IItemRepository itemRepository;

    public ItemService(IItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public boolean handleCreateItem(String name, String description, double price, int quantity, String type, int farmerId, Text actionTarget, String condition) {
        String validationMessage = validateUpsertItem(name, description, price, quantity, type, condition);

        if (!validationMessage.isEmpty()) {

            actionTarget.setText(validationMessage);

            return false;
        }

        Item newItem = null;

        if(type.toUpperCase().equals(ItemStatics.MACHINE)){
            newItem = new Machine(farmerId, name, description, price, quantity, condition);
        }
        else {
            newItem = new Produce(farmerId, name, description, price, quantity);
        }

        itemRepository.createItem(newItem);

        actionTarget.setText("Item Created Successfully");

        return true;
        
    }

    public boolean handleUpdateItem(String name, String description, double price, int quantityAvailable, String type, int farmerId, Text actionTarget, String condition) {
        String validationMessage = validateUpsertItem(name, description, price, quantityAvailable, type, condition);

        if (!validationMessage.isEmpty()) {

            actionTarget.setText(validationMessage);

            return false;
        }

        Item newItem = null;

        Item fetchedItem = itemRepository.getItemByFarmerIdAndName(farmerId, name);

        if(type.toUpperCase().equals(ItemStatics.MACHINE)){
            newItem = new Machine( farmerId, name, description, price, quantityAvailable, condition);

        }
        else {
            newItem = new Produce(farmerId, name, description, price, quantityAvailable);
        }


        if(fetchedItem != null){
            newItem.setId(fetchedItem.getId());
        }
        itemRepository.updateItem(newItem);

   

        actionTarget.setText("Item Updated Successfully");

        return true;
    }

    public void handleUpdateQuantityAvailable(int itemId, int quantityAvailable) {
        itemRepository.updateQuantityAvailable(itemId, quantityAvailable);
    }


    public Item handleGetItemById(int itemId) {
        return itemRepository.getItemById(itemId);
    }

    public List<Item> handleGetItemsByFarmerId(int farmerId) {
        return itemRepository.getItemsByFarmerId(farmerId);
    }

    public List<Item> handleGetAllItems() {
        
        List<Item> fetchedItems = itemRepository.getAllItems();

        List<Item> items = new ArrayList<>();
        
        for (Item item : fetchedItems) {
            if(item.getQuantityAvailable() > 0){
                items.add(item);
            }
        }

        return items;
    }

    public List<Item> handleGetItemsByIds(List<Integer> itemIds) {
        return itemRepository.getItemsByIds(itemIds);
    }
    
    public void handleDeleteItem(Item item) {
        itemRepository.deleteItem(item);
        // actionTarget.setText("Item Deleted Successfully");
    }

    private String validateUpsertItem(String name, String description, double price, int quantity, String type, String condition) {
        StringBuilder missingFields = new StringBuilder("Missing fields: ");

        if (name == null || name.trim().isEmpty()) missingFields.append("\nName, ");

        if (description == null || description.trim().isEmpty()) missingFields.append("\nDescription, ");

        if (price <= 0) missingFields.append("\nPrice, ");

        if (quantity <= 0) missingFields.append("\nQuantity, ");


        if (type == null || type.trim().isEmpty()) {missingFields.append("\nType, ");}

        if (type != null && type.toUpperCase().equals(ItemStatics.MACHINE) && (condition == null || condition.trim().isEmpty())) {missingFields.append("\nCondition, ");}

        if (missingFields.length() > "Missing fields: ".length()) {

            missingFields.setLength(missingFields.length() - 2);

            return missingFields.toString();

        } else {
            // Return empty string if no fields are missing
            return "";
        }
    }

    
}