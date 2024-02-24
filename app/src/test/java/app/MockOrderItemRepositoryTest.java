package app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import models.OrderItem;
import repositories.orderItemRepository.MockOrderItemRepository;



public class MockOrderItemRepositoryTest {

    private MockOrderItemRepository orderItemRepository;

    @BeforeEach
    public void setUp() {
        orderItemRepository = new MockOrderItemRepository();
    }

    @Test
    public void testCreateOrderItem() {
        OrderItem newOrderItem = new OrderItem(1, 1, 5, 100.0, "2024-02-24");
        orderItemRepository.createOrderItem(newOrderItem);
        OrderItem retrievedOrderItem = orderItemRepository.getOrderItemById(1);
        assertNotNull(retrievedOrderItem);
        assertEquals(1, retrievedOrderItem.getItemId());
        assertEquals(1, retrievedOrderItem.getCustomerId());
        assertEquals(5, retrievedOrderItem.getQuantity());
        assertEquals(100.0, retrievedOrderItem.getPrice(), 0.001);
        assertEquals("2024-02-24", retrievedOrderItem.getDateOrdered());
        assertEquals(false, retrievedOrderItem.hasBeenPurchased());
    }

    @Test
    public void testGetOrderItemByItemIdAndCustomerIdAndHasNotBeenPurchased() {
        OrderItem newOrderItem = new OrderItem(1, 1, 5, 100.0, "2024-02-24");
        orderItemRepository.createOrderItem(newOrderItem);
        OrderItem retrievedOrderItem = orderItemRepository.getOrderItemByItemIdAndCustomerIdAndHasNotBeenPurchased(1, 1);
        assertNotNull(retrievedOrderItem);
        assertEquals(1, retrievedOrderItem.getItemId());
        assertEquals(1, retrievedOrderItem.getCustomerId());
        assertEquals(5, retrievedOrderItem.getQuantity());
        assertEquals(100.0, retrievedOrderItem.getPrice(), 0.001);
        assertEquals("2024-02-24", retrievedOrderItem.getDateOrdered());
        assertEquals(false, retrievedOrderItem.hasBeenPurchased());
    }

    @Test
    public void testGetOrderItemsByCustomerId() {
        OrderItem newOrderItem = new OrderItem(1, 1, 5, 100.0, "2024-02-24");
        orderItemRepository.createOrderItem(newOrderItem);
        List<OrderItem> orderItems = orderItemRepository.getOrderItemsByCustomerId(1);
        assertNotNull(orderItems);
        assertEquals(1, orderItems.size());
        assertEquals(1, orderItems.get(0).getCustomerId());
    }

    @Test
    public void testGetOrderItemsByItemIds() {
        OrderItem newOrderItem1 = new OrderItem(1, 1, 5, 100.0, "2024-02-24");
        OrderItem newOrderItem2 = new OrderItem(2, 1, 3, 50.0, "2024-02-25");
        orderItemRepository.createOrderItem(newOrderItem1);
        orderItemRepository.createOrderItem(newOrderItem2);
        List<Integer> itemIds = new ArrayList<>();
        itemIds.add(1);
        itemIds.add(2);
        List<OrderItem> orderItems = orderItemRepository.getOrderItemsByItemIds(itemIds);
        assertNotNull(orderItems);
        assertEquals(2, orderItems.size());
        assertEquals(1, orderItems.get(0).getItemId());
        assertEquals(2, orderItems.get(1).getItemId());
    }


    @Test
    public void testDeleteOrderItem() {
        OrderItem newOrderItem = new OrderItem(1, 1, 5, 100.0, "2024-02-24");
        orderItemRepository.createOrderItem(newOrderItem);
        orderItemRepository.deleteOrderItem(1, 1);
        assertNull(orderItemRepository.getOrderItemById(1));
    }

    @Test
    public void testGetOrderItemById() {
        OrderItem newOrderItem = new OrderItem(1, 1, 5, 100.0, "2024-02-24");
        orderItemRepository.createOrderItem(newOrderItem);
        OrderItem retrievedOrderItem = orderItemRepository.getOrderItemById(1);
        assertNotNull(retrievedOrderItem);
        assertEquals(1, retrievedOrderItem.getId());
    }
}
