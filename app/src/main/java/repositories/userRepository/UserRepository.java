package repositories.userRepository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Customer;
import models.Farmer;
import models.User;
import statics.DbConfig;
import statics.UserRoles;
import utils.DatabaseUtil;

public class UserRepository implements IUserRepository{

    public void createUser(User user) {
        String sql = "INSERT INTO users (first_name, last_name, username, password, role) VALUES (?, ?, ?, ?, ?)";
    
        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

             String role = "";

             if(user instanceof Farmer){
                role = UserRoles.FARMER;
             }
             else {
                role = UserRoles.CUSTOMER;
             }

                pstmt.setString(1, user.getFirstName());

                pstmt.setString(2, user.getLastName());

                pstmt.setString(3, user.getUsername());

                pstmt.setString(4, user.getPassword()); 

                pstmt.setString(5, role);

                pstmt.executeUpdate();

                System.out.println("User added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
  
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);

             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
   
                User user = parseUser(rs);

                return user;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);

             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = parseUser(rs);

                return user;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);

             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = parseUser(rs);

                return user;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public void updateUser(User user, String role) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, password = ?, role = ? WHERE username = ?";
    
        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);

             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getFirstName());

            pstmt.setString(2, user.getLastName());

            pstmt.setString(3, user.getPassword()); 

            pstmt.setString(4, role); 

            pstmt.setString(5, user.getUsername());
            
            
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("User not found.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
    
        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);

             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private User parseUser(ResultSet rs) throws SQLException {
        String role = rs.getString("role");

        User user = null;

        if(role.equals(UserRoles.FARMER)){
           user = new Farmer(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("username"),
                rs.getString("password"));

            user.setId(rs.getInt("id"));
        }
        else if(role.equals(UserRoles.CUSTOMER)){
            user = new Customer(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("username"),
                rs.getString("password"));

            user.setId(rs.getInt("id"));
        }
        return user;
    }
}
