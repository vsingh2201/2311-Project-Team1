package controllers;

import java.util.List;

import models.Item;
import models.composite_responses.OrderItemResponse;
import repositories.itemRepository.ItemRepository;
import repositories.itemRepository.MockItemRepository;
import repositories.orderItemRepository.MockOrderItemRepository;
import repositories.orderItemRepository.OrderItemRepository;
import services.ItemService;
import services.OrderService;

public class OrderController {

    private static OrderController instance;
    private ItemService itemService;
    private OrderService orderService;

    public OrderController(boolean isMock) {
        if(isMock) {
            this.itemService = new ItemService(new MockItemRepository());
            this.orderService = new OrderService(new MockOrderItemRepository(), itemService);
        } else {
            this.itemService = new ItemService(new ItemRepository());
            this.orderService = new OrderService(new OrderItemRepository(), itemService);
        }
    }

    public static OrderController getInstance(boolean isMock) {
        if (instance == null) {
            instance = new OrderController(isMock);
        }
        return instance;
    }

    public void addToCart(Item item, int quantity, int userId) {
        orderService.createOrderItem(item.getId(), userId, quantity, item.getPrice());
    }

    public List<OrderItemResponse> viewCart(int userId) {
        return orderService.getCustomerCart(userId);
    }

    public void removeFromCart(int itemId, int userId) {
        orderService.deleteOrderItem(itemId, userId);
    }

    public void placeOrder(int userId) {
        orderService.placeOrder(userId);
    }
    
    public List<OrderItemResponse> viewOrderHistory(int userId) {
        List<OrderItemResponse> orderItems =  orderService.getOrderHistory(userId);

        return orderItems;
    }

    public List<OrderItemResponse> viewSalesHistory(int userId) {
        List<OrderItemResponse> orderItems =  orderService.getSalesHistory(userId);

        return orderItems;
    }

    public void reduceQuantityBy1(int orderItemId, int userId) {
        orderService.reduceQuantity(orderItemId, userId);
    }

    public void increaseQuantityBy1(int orderItemId, int userId) {
        orderService.increaseQuantity(orderItemId, userId);
    }
    
    public int getQuantityAvailable(int itemId) {
        return itemService.handleGetItemById(itemId).getQuantityAvailable();
    }

    public int getTotalCartQuantity(int userId) {
        List<OrderItemResponse> items  = viewCart(userId);

        int total = 0;

        for (OrderItemResponse item : items) {
            total += item.getQuantity();
        }

        return total;
    }
}