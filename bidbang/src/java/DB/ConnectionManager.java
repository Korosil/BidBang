package DB;

import java.sql.*;
import java.util.*;

public class ConnectionManager {

    static Connection con;
    static String url;

    public static Connection getConnection() {

        try {
            String url = "jdbc:mysql://localhost:3306/BidBang?useUnicode=true&characterEncoding=UTF-8";

            Class.forName("com.mysql.jdbc.Driver");

            try {
                con = DriverManager.getConnection(url, "root", "root");


            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        return con;
    }
}
