package utils;

import models.Customer;
import models.Farmer;
import models.Item;
import models.Machine;
import models.Produce;
import models.User;
import repositories.itemRepository.IItemRepository;
import repositories.itemRepository.ItemRepository;
import repositories.userRepository.IUserRepository;
import repositories.userRepository.UserRepository;
import statics.ItemStatics;

public class DatabaseSeedingUtil {

    private IUserRepository userRepository;
    private IItemRepository itemRepository;

    public DatabaseSeedingUtil() {
        userRepository = new UserRepository();
        itemRepository = new ItemRepository();
    }

    public void seedDatabase() {
        DatabaseSetupUtil.createTables();
        seedUsersTable();
        seedItemsTable();
    }

    public void seedUsersTable() {
        User[] users = {
            new Farmer("John", "Doe", "johndoe", "password123"),
            new Customer("Jane", "Doe", "janedoe", "password456"),
            new Farmer("Jim", "Beam", "jimbeam", "password789")
        };

        for (User user : users) {
            userRepository.createUser(user);
        }

        System.out.println("Users table seeded successfully.");
    }

    public void seedItemsTable() {
        Item[] items = new Item[4]; 

        // Add Machine instances
        items[0] = new Machine(1, "Tractor", "Heavy-duty tractor", 10000.00, 2, ItemStatics.FAIRLY_USED);
        items[1] = new Machine(1, "Harvester", "Crop harvester", 20000.00, 1, ItemStatics.NEW);

        // Add Produce instances
        items[2] = new Produce(1, "Corn", "Fresh corn", 2.50, 150);
        items[3] = new Produce(2, "Tomatoes", "Organic tomatoes", 3.00, 100);

        for (Item item : items) {
            this.itemRepository.createItem(item);
        }

        System.out.println("Items table seeded successfully.");
    }
    

}