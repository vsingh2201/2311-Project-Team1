package repositories.itemRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import models.Item;
import models.Machine;
import models.Produce;
import statics.DbConfig;
import utils.DatabaseUtil;

public class ItemRepository implements IItemRepository {

    public void createItem(Item item) {

        boolean itemExistsForFarmer = getItemByFarmerIdAndName(item.getFarmerId(), item.getName()) != null;

        if (itemExistsForFarmer) {
            updateItem(item);
            return;
        }

        String sql = "INSERT INTO items (farmer_id, name, description, price, quantity_available, condition, type) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER,
                DbConfig.DB_PASSWORD);

                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, item.getFarmerId());
            pstmt.setString(2, item.getName());
            pstmt.setString(3, item.getDescription());
            pstmt.setDouble(4, item.getPrice());
            pstmt.setInt(5, item.getQuantityAvailable());

            if (item instanceof Machine) {
                pstmt.setString(6, ((Machine) item).getCondition());
                pstmt.setString(7, "MACHINE");
            } else {
                pstmt.setNull(6, Types.VARCHAR); // Assuming 'condition' is not used for Produce
                pstmt.setString(7, "PRODUCE");
            }

            pstmt.executeUpdate();
            System.out.println("Item created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Item getItemById(int id) {
        String sql = "SELECT * FROM items WHERE id = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER,
                DbConfig.DB_PASSWORD);

                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Item item = parseItem(rs);

                return item;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Item> getAllItems() {
        String sql = "SELECT * FROM items";

        List<Item> items = new ArrayList<>();

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER,
                DbConfig.DB_PASSWORD);

                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Item item = parseItem(rs);

                items.add(item);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return items;
    }

    public Item getItemByName(String name) {
        String sql = "SELECT * FROM items WHERE name = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER,
                DbConfig.DB_PASSWORD);

                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Item item = parseItem(rs);

                return item;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Item> getItemsByIds(List<Integer> itemIds) {
        String sql = "SELECT * FROM items WHERE id = ANY(?)";

        List<Item> items = new ArrayList<>();

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER,
                DbConfig.DB_PASSWORD);

                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setArray(1, conn.createArrayOf("INTEGER", itemIds.toArray()));

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Item item = parseItem(rs);

                items.add(item);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return items;
    }

    public void updateItem(Item item) {

        String sql = "UPDATE items SET name = ?, description = ?, price = ?, quantity_available = ?, type = ?";

        if (item instanceof Machine) {
            sql += ", condition = ?";
        }

        sql += " WHERE id = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER,
                DbConfig.DB_PASSWORD);

                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getDescription());
            pstmt.setDouble(3, item.getPrice());
            pstmt.setInt(4, item.getQuantityAvailable());

            if (item instanceof Machine) {
                pstmt.setString(5, "MACHINE");
                pstmt.setString(6, ((Machine) item).getCondition());
                pstmt.setInt(7, item.getId());
            } else if (item instanceof Produce) {
                pstmt.setString(5, "PRODUCE");
                pstmt.setInt(6, item.getId());
            } else {
                throw new IllegalArgumentException("Unhandled type of Item");
            }

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Item updated successfully.");
            } else {
                System.out.println("No record found with the given ID.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteItem(Item item) {

        String sql = "DELETE FROM items WHERE farmer_id = ? AND name = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER,
                DbConfig.DB_PASSWORD);

                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, item.getFarmerId());

            pstmt.setString(2, item.getName());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Item deleted successfully.");
            } else {
                System.out.println("No record found with the given ID.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public Item getItemByFarmerIdAndName(int farmerId, String name) {
        String sql = "SELECT * FROM items WHERE farmer_id = ? AND name = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER,
                DbConfig.DB_PASSWORD);

                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, farmerId);
            pstmt.setString(2, name);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Item item = parseItem(rs);

                return item;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Item> getItemsByFarmerId(int farmerId) {
        String sql = "SELECT * FROM items WHERE farmer_id = ?";

        List<Item> items = new ArrayList<>();

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER,
                DbConfig.DB_PASSWORD);

                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, farmerId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Item item = parseItem(rs);

                items.add(item);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return items;
    }

    public void updateQuantityAvailable(int itemId, int quantityAvailable) {

        String sql = "UPDATE items SET quantity_available = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER,
                DbConfig.DB_PASSWORD);

                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantityAvailable);
            pstmt.setInt(2, itemId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Quantity available updated successfully.");
            } else {
                System.out.println("No record found with the given ID.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Item parseItem(ResultSet rs) throws SQLException {

        String type = rs.getString("type");
        if ("MACHINE".equals(type)) {
            Machine machine = new Machine(
                    rs.getInt("farmer_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantity_available"),
                    rs.getString("condition"));
            machine.setId(rs.getInt("id"));

            return machine;
        } else if ("PRODUCE".equals(type)) {
            Produce produce = new Produce(
                    rs.getInt("farmer_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantity_available"));

            produce.setId(rs.getInt("id"));

            return produce;
        } else {
            throw new SQLException("Unknown type of item");
        }
    }

}
