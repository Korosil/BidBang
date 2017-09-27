package DAOs;

import Beans.SearchBean;
import DB.ConnectionManager;
import java.text.*;
import java.util.*;
import java.sql.*;
import java.util.ArrayList;

public class SearchDAO {

    static Connection currentCon = null;
    static ResultSet rs = null;

    public static SearchBean search(SearchBean bean) {

        Statement stmt = null;
        ArrayList al = null;
        ArrayList search_array = new ArrayList();
            
        String search_item = bean.getSearch_item();

        String searchQuery = "select * from auction where itemID like '%" + search_item + "%'"
                + "OR name like '%" + search_item + "%' "
                + "OR category like '%" + search_item + "%'"
                + "OR seller_name like '%" + search_item + "%' ;";

        System.out.println("Searching termn: " + search_item);
        try {
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();
            rs = stmt.executeQuery(searchQuery);
            
            while (rs.next()) {
                al = new ArrayList();
                
                al.add(rs.getInt("itemID"));
                al.add(rs.getString("name"));
                al.add(rs.getFloat("currently"));
                al.add(rs.getString("country"));
                
                search_array.add(al);
                
            }
            
            bean.setSearch_rs(search_array);

        } catch (Exception ex) {
            System.out.println("Search failed: An Exception has occurred! " + ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
                stmt = null;
            }

            if (currentCon != null) {
                try {
                    currentCon.close();
                } catch (Exception e) {
                }

                currentCon = null;
            }
        }

        return bean;
    }
}
