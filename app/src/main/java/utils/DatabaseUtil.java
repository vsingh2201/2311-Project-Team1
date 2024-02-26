package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    public static Connection connect(String url, String user, String password) {
        try {
        
            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {

            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path.");

            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println("Connection failure.");

            e.printStackTrace();
            
        }
        return null;
    }


}