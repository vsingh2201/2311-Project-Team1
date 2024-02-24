package app;

import org.junit.jupiter.api.Test;

import repositories.itemRepository.MockItemRepository;
import models.Item;
import models.Machine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;


public class MockItemRepositoryTest {

	private MockItemRepository itemRepository;

	@BeforeEach
	public void setUp() {
		itemRepository = new MockItemRepository();
	}

	@Test
	public void testCreateItem() {
		Item newItem = new Machine(2, "Plow", "Agricultural plow", 5000.0, 1, "Used");
		itemRepository.createItem(newItem);
		Item retrievedItem = itemRepository.getItemById(5);
		assertNotNull(retrievedItem);
		assertEquals("Plow", retrievedItem.getName());
	}

	@Test
	public void testUpdateItem() {
		Item existingItem = itemRepository.getItemById(1);
		existingItem.setPrice(12000.0);
		itemRepository.updateItem(existingItem);
		Item updatedItem = itemRepository.getItemById(1);
		assertEquals(12000.0, updatedItem.getPrice(), 0.001);
	}

	@Test
	public void testDeleteItem() {
		Item itemToDelete = itemRepository.getItemById(1);
		itemRepository.deleteItem(itemToDelete);
		assertNull(itemRepository.getItemById(1));
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
	public void testGetAllItems() {
		List<Item> allItems = itemRepository.getAllItems();
		assertEquals(4, allItems.size());
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

	@Test
	public void testUpdateQuantityAvailable() {
		itemRepository.updateQuantityAvailable(1, 200);
		Item updatedItem = itemRepository.getItemById(1);
		assertEquals(200, updatedItem.getQuantityAvailable());
	}
}
