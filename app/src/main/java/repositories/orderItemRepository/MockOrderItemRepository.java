package repositories.orderItemRepository;

package repositories.orderItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import models.OrderItem;

public class MockOrderItemRepository implements IOrderItemRepository{

    private static List<OrderItem> orderItems;
    private AtomicInteger orderItemIdCounter;

    public MockOrderItemRepository() {
        this.orderItemIdCounter = new AtomicInteger(1);
        MockOrderItemRepository.orderItems = new ArrayList<>();
    }

    public void createOrderItem(OrderItem orderItem) {
        orderItem.setId(orderItemIdCounter.getAndIncrement());
        orderItems.add(orderItem);
    }

    public OrderItem getOrderItemByItemIdAndCustomerIdAndHasNotBeenPurchased(int itemId, int customerId) {
        return orderItems.stream()
            .filter(orderItem -> orderItem.getItemId() == itemId && orderItem.getCustomerId() == customerId && !orderItem.hasBeenPurchased())
            .findFirst()
            .orElse(null);
    }

    public List<OrderItem> getOrderItemsByCustomerId(int customerId) {
        return orderItems.stream()
            .filter(orderItem -> orderItem.getCustomerId() == customerId)
            .toList();
    }

    public List<OrderItem> getOrderItemsByItemIds(List<Integer> itemIds) {
        return orderItems.stream()
            .filter(orderItem -> itemIds.contains(orderItem.getItemId()))
            .toList();
    }

    public void updateOrderItem(OrderItem orderItem) {
        orderItems.stream()
            .filter(oi -> oi.getId() == orderItem.getId())
            .forEach(oi -> {
                oi.setQuantity(orderItem.getQuantity());
                oi.setPrice(orderItem.getPrice());
                oi.setDateOrdered(orderItem.getDateOrdered());
                oi.setHasBeenPurchased(orderItem.hasBeenPurchased());
            });
    }

    public void deleteOrderItem(int itemId, int customerId) {
        orderItems.removeIf(orderItem -> orderItem.getItemId() == itemId && orderItem.getCustomerId() == customerId);
    }

    public OrderItem getOrderItemById(int id) {
        return orderItems.stream()
            .filter(orderItem -> orderItem.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
}