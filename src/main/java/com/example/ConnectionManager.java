package main.java.com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
    }

    public Connection newConnectionEveryTime() {
        try {
            return DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        } catch (SQLException e) {
            return null;
        }
    }
}
