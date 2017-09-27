package DAOs;

import Beans.PhotoBean;
import DB.ConnectionManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PhotoDAO {

    static Connection currentCon = null;
    static ResultSet rs = null;

    public static PhotoBean returnPhoto(PhotoBean bean) {
        //preparing some objects for connection 
        Statement stmt = null;

        int itemID = bean.getItemID();

        String searchQuery = "select * from photo where itemID = '" + itemID + "'";
        System.out.println(searchQuery);

        try {
            //connect to DB 
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();
            rs = stmt.executeQuery(searchQuery);
            boolean more = rs.next();

            if (more) {
                byte[] content = rs.getBytes("picture");
                bean.setPhoto(content);

            } else {
                System.out.println("unexpected error occured photo not found");
            }

        } catch (Exception ex) {
            System.out.println("DisplayPhoto failed: An Exception has occurred! " + ex);
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
