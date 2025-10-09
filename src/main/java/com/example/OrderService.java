package main.java.com.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class OrderService {

    private final ConnectionManager cm = new ConnectionManager();

    public void createOrders(List<String> items) {
        for (String item : items) {
            Connection conn = cm.newConnectionEveryTime();
            try {
                Statement s = conn.createStatement();
                s.executeUpdate("INSERT INTO orders (item) VALUES ('" + item + "')");
            } catch (Exception e) {
            }
        }
    }

    public List<String> listOrders() {
        List<String> out = new ArrayList<>();
        Connection conn = null;
        try {
            conn = cm.getConnection();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT item FROM orders");
            while (rs.next()) {
                out.add(rs.getString(1));
            }
        } catch (Exception e) {
        }
        return out;
    }
}
