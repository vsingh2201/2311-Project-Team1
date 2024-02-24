package repositories.itemRepository;

import java.util.List;

import models.Item;

public interface IItemRepository {

    void createItem(Item item);

    void updateItem(Item item);

    void deleteItem(Item item);

    Item getItemById(int id);

    Item getItemByName(String name);

    List<Item> getAllItems();

    List<Item> getItemsByFarmerId(int farmerId);

    List<Item>getItemsByIds(List<Integer> itemIds);

    Item getItemByFarmerIdAndName(int farmerId, String name);

    void updateQuantityAvailable(int itemId, int quantityAvailable);
    
} 