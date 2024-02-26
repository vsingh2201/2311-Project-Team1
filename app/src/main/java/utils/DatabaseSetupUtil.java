package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import statics.DbConfig;

public class DatabaseSetupUtil {

    public static void createTables() {
        dropUsersTable();
        createUsersTable();
        dropItemsTable();
        createItemsTable();
        dropOrderItemsTable();
        createOrderItemsTable();    
        dropRatingAndReviewTable();
        createRatingAndReviewTable();
    }


    public static void createUsersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id SERIAL PRIMARY KEY,"
                + "first_name VARCHAR(255),"
                + "last_name VARCHAR(255),"
                + "username VARCHAR(255) UNIQUE NOT NULL,"
                + "password VARCHAR(255),"
                + "role VARCHAR(50)"
                + ");";
    
        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);

            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate();

            System.out.println("Table 'users' created successfully.");

        } catch (SQLException e) {
            System.out.println("Error creating table 'users': " + e.getMessage());
        
        }
    }

    public static void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
        
             Statement stmt = conn.createStatement()) {
             
            stmt.execute(sql);

            System.out.println("Table 'users' dropped successfully.");
            
        } catch (SQLException e) {
            System.out.println("An error occurred while dropping the table: " + e.getMessage());

            e.printStackTrace();
        }
    }
    
    public static void createItemsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS items ("
                + "id SERIAL PRIMARY KEY,"
                + "farmer_id INT,"
                + "name VARCHAR(255),"
                + "description TEXT,"
                + "price DECIMAL(10, 2),"
                + "quantity_available INT,"
                + "type VARCHAR(50),"
                + "condition VARCHAR(50)"
                + ");";
    
        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
        
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.executeUpdate();

            System.out.println("Table 'items' created successfully.");
            
        } catch (SQLException e) {
            System.out.println("Error creating table 'items': " + e.getMessage());
        }
    }

    public static void dropItemsTable() {
        String sql = "DROP TABLE IF EXISTS items";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
        
             Statement stmt = conn.createStatement()) {
             
            stmt.execute(sql);

            System.out.println("Table 'items' dropped successfully.");
            
        } catch (SQLException e) {
            System.out.println("An error occurred while dropping the table: " + e.getMessage());

            e.printStackTrace();
        }
    }

    public static void createOrderItemsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS order_items ("
            + "id SERIAL PRIMARY KEY,"
            + "item_id INT,"
            + "customer_id INT,"
            + "quantity INT,"
            + "price DECIMAL(10, 2),"
            + "date_ordered VARCHAR(255),"
            + "has_been_purchased BOOLEAN DEFAULT FALSE" 
            + ");";

        
        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
        
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.executeUpdate();

            System.out.println("Table 'order_items' created successfully.");
            
        } catch (SQLException e) {
            System.out.println("Error creating table 'order_items': " + e.getMessage());
        }
    }

    public static void dropOrderItemsTable() {
        String sql = "DROP TABLE IF EXISTS order_items";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
        
             Statement stmt = conn.createStatement()) {
             
            stmt.execute(sql);

            System.out.println("Table 'order_items' dropped successfully.");
            
        } catch (SQLException e) {
            System.out.println("An error occurred while dropping the table: " + e.getMessage());

            e.printStackTrace();
        }
    }

    public static void createRatingAndReviewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS rating_and_reviews ("
            + "id SERIAL PRIMARY KEY,"
            + "item_id INT,"
            + "customer_id INT,"
            + "rating INT,"
            + "review TEXT,"
            + "date VARCHAR(255)"
            + ");";

        
        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
        
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.executeUpdate();

            System.out.println("Table 'rating_and_reviews' created successfully.");
            
        } catch (SQLException e) {
            System.out.println("Error creating table 'rating_and_reviews': " + e.getMessage());
        }
    }

    public static void dropRatingAndReviewTable() {
        String sql = "DROP TABLE IF EXISTS rating_and_reviews";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
        
             Statement stmt = conn.createStatement()) {
             
            stmt.execute(sql);

            System.out.println("Table 'rating_and_reviews' dropped successfully.");
            
        } catch (SQLException e) {
            System.out.println("An error occurred while dropping the table: " + e.getMessage());

            e.printStackTrace();
        }
    }
}