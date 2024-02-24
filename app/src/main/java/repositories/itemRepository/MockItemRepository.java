package repositories.itemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import models.Item;
import models.Machine;
import models.Produce;
import statics.ItemStatics;

public class MockItemRepository implements IItemRepository{

    private static List<Item> items;
    private AtomicInteger itemIdCounter;

    public MockItemRepository() {
        this.itemIdCounter = new AtomicInteger(1);
        MockItemRepository.items = new ArrayList<>();

        Item[] items = new Item[4]; 

        // Add Machine instances
        items[0] = new Machine(1, "Tractor", "Heavy-duty tractor", 10000.00, 2, ItemStatics.FAIRLY_USED);
        items[1] = new Machine(1, "Harvester", "Crop harvester", 20000.00, 1, ItemStatics.NEW);

        // Add Produce instances
        items[2] = new Produce(1, "Corn", "Fresh corn", 2.50, 150);
        items[3] = new Produce(2, "Tomatoes", "Organic tomatoes", 3.00, 100);

        for (Item item : items) {
            createItem(item);
        }
    }

    public void createItem(Item item) {
        item.setId(itemIdCounter.getAndIncrement());
        items.add(item);
    }
    

    public void updateItem(Item item) {

        OptionalInt indexOptional = IntStream.range(0, items.size())
            .filter(i -> items.get(i).getId() == item.getId())
            .findFirst();


        if (indexOptional.isPresent()) {
            items.set(indexOptional.getAsInt(), item);
        } else {
            System.out.println("Item with ID " + item.getId() + " not found in Mock Item Repo.");
        }

    }

    public void deleteItem(Item item) {
        items.removeIf(i -> i.getId() == item.getId());
    }

    public Item getItemById(int id) {
        return items.stream()
            .filter(item -> item.getId() == id)
            .findFirst()
            .orElse(null);
        
    }

    public Item getItemByName(String name) {
        return items.stream()
            .filter(item -> item.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

    public List<Item> getAllItems() {
        return items;
    }

    public List<Item> getItemsByFarmerId(int farmerId) {
       List<Item> items = MockItemRepository.items.stream()
            .filter(item -> item.getFarmerId() == farmerId)
            .toList();

        return items;
    }

    public List<Item> getItemsByIds(List<Integer> itemIds) {
        return items.stream()
            .filter(item -> itemIds.contains(item.getId()))
            .toList();
    }

    public Item getItemByFarmerIdAndName(int farmerId, String name) {
        return items.stream()
            .filter(item -> item.getFarmerId() == farmerId && item.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

    public void updateQuantityAvailable(int itemId, int quantityAvailable) {
        items.stream()
            .filter(item -> item.getId() == itemId)
            .forEach(item -> item.setQuantityAvailable(quantityAvailable));
    }
    
}