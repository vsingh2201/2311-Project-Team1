package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import models.Item;
import models.OrderItem;
import models.composite_responses.OrderItemResponse;
import repositories.orderItemRepository.IOrderItemRepository;

public class OrderService {
    private IOrderItemRepository orderItemRepository;
    private ItemService itemService;

    public OrderService(IOrderItemRepository orderItemRepository, ItemService itemService) {
        this.orderItemRepository = orderItemRepository;
        this.itemService = itemService;
    }

    public void createOrderItem(int itemId, int customerId, int quantity, double price) {

        OrderItem orderItem = orderItemRepository.getOrderItemByItemIdAndCustomerIdAndHasNotBeenPurchased(itemId, customerId);

        Item item = itemService.handleGetItemById(itemId);

        if(orderItem != null){
            List<OrderItemResponse> orderItems = getCustomerCart(customerId);

            int quantityToSet = orderItem.getQuantity() + quantity;

            for (OrderItemResponse orderItemResponse : orderItems) {
                if(orderItemResponse.getOrderItemId() == orderItem.getId()){
                    if(orderItemResponse.getQuantity() + quantity > item.getQuantityAvailable()){
                        quantityToSet = item.getQuantityAvailable();
                    }
                }
            }
            
            orderItem.setQuantity(quantityToSet);
            orderItem.setDateOrdered(new Date().getTime()+"");
            orderItemRepository.updateOrderItem(orderItem);

        } else {
            if(item.getQuantityAvailable() < quantity){
                return;
            }
            orderItem = new OrderItem(itemId, customerId, quantity, price, new Date().getTime()+"");
            orderItemRepository.createOrderItem(orderItem);
        }
    }

    public List<OrderItemResponse> getCustomerCart(int customerId) {
        List<OrderItem> orderItems = orderItemRepository.getOrderItemsByCustomerId(customerId);

        List<OrderItemResponse> orderItemResponses = new ArrayList<OrderItemResponse>();

        HashMap<Integer, OrderItemResponse> orderItemMap = new HashMap<Integer, OrderItemResponse>();

        for (OrderItem orderItem : orderItems) {

            if(!orderItem.hasBeenPurchased()){

                if(orderItemMap.containsKey(orderItem.getItemId())){

                    OrderItemResponse orderItemResponse = orderItemMap.get(orderItem.getItemId());
                    orderItemResponse.setQuantity(orderItemResponse.getQuantity() + orderItem.getQuantity());

                } else {
                    orderItemMap.put(orderItem.getItemId(), getOrderItemResponse(orderItem));
                }
            }
        }

        for (OrderItemResponse orderItemResponse : orderItemMap.values()) {
            orderItemResponses.add(orderItemResponse);
        }

        return orderItemResponses;
    }

    public List<OrderItemResponse> getOrdersByCustomerIdIncludingPurchased(int customerId) {
        List<OrderItem> orderItems = orderItemRepository.getOrderItemsByCustomerId(customerId);

        List<OrderItemResponse> orderItemResponses = new ArrayList<OrderItemResponse>();

        for (OrderItem orderItem : orderItems) {
            orderItemResponses.add(getOrderItemResponse(orderItem));
        }

        return orderItemResponses;
    }


    public void updateOrder(List<OrderItem> orderItems, String dateTime) {

        String date = new Date().getTime()+"";

        for (OrderItem orderItem : orderItems) {
            orderItem.setDateOrdered(date);
            orderItemRepository.updateOrderItem(orderItem);
        }
    }

    public void placeOrder(int customerId) {
        List<OrderItem> orderItems = new ArrayList<OrderItem>();

        List<OrderItem> customerCart = orderItemRepository.getOrderItemsByCustomerId(customerId);

        HashMap<Integer, OrderItemResponse> orderItemMap = new HashMap<Integer, OrderItemResponse>();

        for (OrderItem orderItem : customerCart) {
            if(!orderItem.hasBeenPurchased()){

                if(orderItemMap.containsKey(orderItem.getItemId())){
                    OrderItemResponse orderItemResponse = orderItemMap.get(orderItem.getItemId());
                    orderItemResponse.setQuantity(orderItemResponse.getQuantity() + orderItem.getQuantity());

                } else {
                    orderItemMap.put(orderItem.getItemId(), getOrderItemResponse(orderItem));
                }
            }
        }


        for (OrderItemResponse orderItemResponse : orderItemMap.values()) {
            OrderItem orderItem = new OrderItem(orderItemResponse.getItemId(), orderItemResponse.getCustomerId(), orderItemResponse.getQuantity(), orderItemResponse.getPrice(), new Date().getTime()+"");

            orderItem.setId(orderItemResponse.getOrderItemId());

            orderItem.setHasBeenPurchased(true);

            orderItems.add(orderItem);

            Item item = itemService.handleGetItemById(orderItem.getItemId());

            itemService.handleUpdateQuantityAvailable(item.getId(), item.getQuantityAvailable() - orderItem.getQuantity());
        }

        updateOrder(orderItems, new Date().getTime()+"");
    }

    public List<OrderItemResponse> getSalesHistory(int farmerId) {
        List<Item> farmersItems = itemService.handleGetItemsByFarmerId(farmerId);
        
        List<Integer> itemIds = new ArrayList<Integer>();

        for (Item item : farmersItems) {
            itemIds.add(item.getId());
        }

        List<OrderItem> orderItems = orderItemRepository.getOrderItemsByItemIds(itemIds);

        List<OrderItemResponse> orderItemResponses = new ArrayList<OrderItemResponse>();

        for (OrderItem orderItem : orderItems) {
            if(orderItem.hasBeenPurchased())
                orderItemResponses.add(getOrderItemResponse(orderItem));
        }

        return orderItemResponses;
    }

    public List<OrderItemResponse> getOrderHistory(int customerId) {
        List<OrderItem> orderItems = orderItemRepository.getOrderItemsByCustomerId(customerId);

        List<OrderItemResponse> orderItemResponses = new ArrayList<OrderItemResponse>();

        for (OrderItem orderItem : orderItems) {
            if(orderItem.hasBeenPurchased())
                orderItemResponses.add(getOrderItemResponse(orderItem));
        }

        return orderItemResponses;
    }

    public void deleteOrderItem(int itemId, int customerId) {
        orderItemRepository.deleteOrderItem(itemId, customerId);
    }

    private OrderItemResponse getOrderItemResponse(OrderItem orderItem) {
        Item item = itemService.handleGetItemById(orderItem.getItemId());

       return new OrderItemResponse(orderItem.getItemId(), orderItem.getId(), item.getFarmerId(), item.getName(),
        item.getDescription(), item.getPrice(), orderItem.getCustomerId(),
         orderItem.getQuantity(), orderItem.getDateOrdered());
    }


    public void reduceQuantity(int orderItemId, int userId) {
        OrderItem orderItem = orderItemRepository.getOrderItemById(orderItemId
        );

        if(orderItem.getQuantity() > 1){
            orderItem.setQuantity(orderItem.getQuantity() - 1);
            orderItem.setDateOrdered(new Date().getTime()+"");
            orderItemRepository.updateOrderItem(orderItem);
        }

        else if(orderItem.getQuantity() == 1){
            orderItemRepository.deleteOrderItem(orderItem.getItemId(), userId);
        }


    }

    public void increaseQuantity(int orderItemId, int customerId) {
        OrderItem orderItem = orderItemRepository.getOrderItemById(orderItemId);

        Item item = itemService.handleGetItemById(orderItem.getItemId());

        List<OrderItemResponse> orderItems = getCustomerCart(customerId);

            int quantityToSet = calculateQuantityToSet(orderItemId, orderItems, item, orderItem.getQuantity() + 1);
            
            orderItem.setQuantity(quantityToSet);
            orderItem.setDateOrdered(new Date().getTime()+"");
            orderItemRepository.updateOrderItem(orderItem);
    }

    private int calculateQuantityToSet(int orderItemId, List<OrderItemResponse> orderItems, Item item, int initialQuantity) {
        for (OrderItemResponse orderItemResponse : orderItems) {
            if (orderItemResponse.getOrderItemId() == orderItemId) {
                if (initialQuantity > item.getQuantityAvailable()) {
                    return item.getQuantityAvailable();
                }
            }
        }
        return initialQuantity;
    }
    
}