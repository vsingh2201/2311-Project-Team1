package repositories.orderItemRepository;


import models.OrderItem;
import java.util.List;

public interface IOrderItemRepository {
    void createOrderItem(OrderItem orderItem);
    OrderItem getOrderItemByItemIdAndCustomerIdAndHasNotBeenPurchased(int itemId, int customerId);
    List<OrderItem> getOrderItemsByCustomerId(int customerId);
    List<OrderItem>getOrderItemsByItemIds(List<Integer> itemIds);
    void updateOrderItem(OrderItem orderItem);
    void deleteOrderItem(int itemId, int customerId);
    OrderItem getOrderItemById(int id);
}

