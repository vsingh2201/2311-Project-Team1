package repositories.orderItemRepository;




import models.OrderItem;
import utils.DatabaseUtil;
import statics.DbConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemRepository implements IOrderItemRepository {

    public void createOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_items (item_id, customer_id, quantity, price, date_ordered, has_been_purchased) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderItem.getItemId());
            pstmt.setInt(2, orderItem.getCustomerId());
            pstmt.setInt(3, orderItem.getQuantity());
            pstmt.setDouble(4, orderItem.getPrice());
            pstmt.setString(5, orderItem.getDateOrdered());
            pstmt.setBoolean(6, false);

            pstmt.executeUpdate();
            System.out.println("Order item added successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating order item: " + e.getMessage());
        }
    }

    public OrderItem getOrderItemByItemIdAndCustomerIdAndHasNotBeenPurchased(int itemId, int customerId) {
        String sql = "SELECT * FROM order_items WHERE item_id = ? AND customer_id = ? AND has_been_purchased = false";
        OrderItem orderItem = null;

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemId);
            pstmt.setInt(2, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                orderItem = new OrderItem(
                    rs.getInt("item_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("price"),
                    rs.getString("date_ordered")
                );
                orderItem.setId(rs.getInt("id"));
                orderItem.setHasBeenPurchased(rs.getBoolean("has_been_purchased"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving order item: " + e.getMessage());
        }
        return orderItem;
    }

    public List<OrderItem> getOrderItemsByCustomerId(int customerId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE customer_id = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderItem orderItem = new OrderItem(
                    rs.getInt("item_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("price"),
                    rs.getString("date_ordered")
                );
                orderItem.setId(rs.getInt("id"));
                orderItem.setHasBeenPurchased(rs.getBoolean("has_been_purchased"));

                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving order items for customer ID: " + customerId + " - " + e.getMessage());
        }
        return orderItems;
    }

    public List<OrderItem> getOrderItemsByItemIds(List<Integer> itemIds) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE item_id IN (";

        for (int i = 0; i < itemIds.size(); i++) {
            if (i == itemIds.size() - 1) {
                sql += "?)";
            } else {
                sql += "?, ";
            }
        }

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < itemIds.size(); i++) {
                pstmt.setInt(i + 1, itemIds.get(i));
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderItem orderItem = new OrderItem(
                    rs.getInt("item_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("price"),
                    rs.getString("date_ordered")
                );
                orderItem.setId(rs.getInt("id"));
                orderItem.setHasBeenPurchased(rs.getBoolean("has_been_purchased"));

                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving order items for item IDs: " + itemIds + " - " + e.getMessage());
        }
        return orderItems;
    }
    
    public OrderItem getOrderItemById(int id){

        String sql = "SELECT * FROM order_items WHERE id = ?";

        OrderItem orderItem = null;

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                orderItem = new OrderItem(
                    rs.getInt("item_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("price"),
                    rs.getString("date_ordered")
                );
                orderItem.setId(rs.getInt("id"));
                orderItem.setHasBeenPurchased(rs.getBoolean("has_been_purchased"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving order item: " + e.getMessage());
        }
        return orderItem;

    }

    public void updateOrderItem(OrderItem orderItem) {
        String sql = "UPDATE order_items SET quantity = ?, price = ?, date_ordered = ?, has_been_purchased = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderItem.getQuantity());
            pstmt.setDouble(2, orderItem.getPrice());
            pstmt.setString(3, orderItem.getDateOrdered());
            pstmt.setBoolean(4, orderItem.hasBeenPurchased());
            pstmt.setInt(5, orderItem.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Order item updated successfully.");
            } else {
                System.out.println("No order item found with provided ID: " + orderItem.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error updating order item: " + e.getMessage());
        }


    }

    public void deleteOrderItem(int itemId, int customerId) {
        String sql = "DELETE FROM order_items WHERE item_id = ? AND customer_id = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemId);
            pstmt.setInt(2, customerId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Order item deleted successfully.");
            } else {
                System.out.println("No order item found with provided item ID and customer ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting order item: " + e.getMessage());
        }
    }
}

