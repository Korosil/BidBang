package DAOs;

import Beans.CategoryBean;
import DB.ConnectionManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoryDAO {
    static Connection currentCon = null;
    static ResultSet rs = null;

    public static CategoryBean returnCategoryItems(CategoryBean bean) {

        Statement stmt = null;
        ArrayList al = null;
        ArrayList category_array = new ArrayList();
            
        String category = bean.getCategory();
        System.out.println("Category to display: " + category);
        String searchQuery = "select * from auction where category = '" + category + "'";
        System.out.println(searchQuery);
        
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
                
                category_array.add(al);
                
            }
            bean.setCategory(category);
            bean.setCategory_rs(category_array);

        } catch (Exception ex) {
            System.out.println("Category display failed: An Exception has occurred! " + ex);
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
