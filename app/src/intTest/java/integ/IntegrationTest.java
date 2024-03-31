package integ;

import utils.DatabaseSeedingUtil;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import models.Item;
import models.User;
import repositories.itemRepository.ItemRepository;
import repositories.userRepository.UserRepository;


public class IntegrationTest {
    private static UserRepository userRepository;
    private static ItemRepository itemRepository;
    
    @BeforeAll
    public static void setUp() {
    	// Set up database
        DatabaseSeedingUtil db = new DatabaseSeedingUtil();
        db.seedDatabase();
        userRepository = new UserRepository();
        itemRepository = new ItemRepository();
    }
    
    // Test Cases for UserRepository
    
    @Test
    public void testGetUserById() {
        User user = userRepository.getUserById(1);
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("johndoe", user.getUsername());
        assertEquals("password123", user.getPassword());
    }
    
    @Test
    public void testGetUserByUsername() {
        User user = userRepository.getUserByUsername("janedoe");
        assertNotNull(user);
        assertEquals("Jane", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("janedoe", user.getUsername());
        assertEquals("password456", user.getPassword());
    }
    
    @Test
    public void testValidateUser() {
        User validUser = userRepository.validateUser("jimbeam", "password789");
        assertNotNull(validUser);
        assertEquals("Jim", validUser.getFirstName());
        assertEquals("Beam", validUser.getLastName());
        assertEquals("jimbeam", validUser.getUsername());
        assertEquals("password789", validUser.getPassword());

        User invalidUser = userRepository.validateUser("jimbeam", "wrongpassword");
        assertNull(invalidUser);
    }
    
    // Test cases for ItemRepository
    @Test
	public void testGetAllItems() {
		List<Item> allItems = itemRepository.getAllItems();
		assertEquals(4, allItems.size());
	}
    
    @Test
	public void testGetItemById() {
		Item item = itemRepository.getItemById(2);
		assertNotNull(item);
		assertEquals("Harvester", item.getName());
	}
    
    @Test
	public void testGetItemByName() {
		Item item = itemRepository.getItemByName("Corn");
		assertNotNull(item);
		assertEquals("Corn", item.getName());
	}
    @Test
	public void testGetItemsByIds() {
		List<Integer> itemIds = new ArrayList<>();
		itemIds.add(1);
		itemIds.add(2);
		List<Item> items = itemRepository.getItemsByIds(itemIds);
		assertEquals(2, items.size());
	}

	@Test
	public void testGetItemByFarmerIdAndName() {
		Item item = itemRepository.getItemByFarmerIdAndName(1, "Corn");
		assertNotNull(item);
		assertEquals("Corn", item.getName());
		assertEquals(1, item.getFarmerId());
	}

    
}
