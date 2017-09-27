package DAOs;

import Beans.UnverifiedUsersBean;
import DB.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;

public class UnverifiedUsersDAO {

    static Connection currentCon = null;
    static ResultSet rs = null;

    public static UnverifiedUsersBean unverifiedUsers(UnverifiedUsersBean bean) {

        PreparedStatement stmt = null;
        ArrayList<String> unverified_users_array = new ArrayList<String>();

        String searchQuery;
        searchQuery = "select * from user where verified = 0";

        try {
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.prepareStatement(searchQuery);
            rs = stmt.executeQuery(searchQuery);

            while (rs.next()) {
                unverified_users_array.add(rs.getString("username"));
            }
            bean.setUnverifiedUsers_rs(unverified_users_array);

        } catch (Exception ex) {
            System.out.println("Select unverified failed: An Exception has occurred! " + ex);
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

    public static void verifyUsers(UnverifiedUsersBean bean) {
        PreparedStatement stmt = null;
        ArrayList al = null;
        ArrayList<String> users_to_verify = new ArrayList<String>();
        String updateQuery;
        String user_to_update;
        int affected_rows;

        users_to_verify = bean.getUnverifiedUsers_rs();

        if (!users_to_verify.isEmpty()) {

            try {
                currentCon = ConnectionManager.getConnection();
                for (int i = 0; i < users_to_verify.size(); i++) {
                    affected_rows = 0;
                    stmt = null;
                    updateQuery = null;
                    user_to_update = users_to_verify.get(i);

                    updateQuery = "update user set verified = 1 where username = ? and verified = 0";
                    //here we can move the updateQuery initiate outside of try propably
                    stmt = currentCon.prepareStatement(updateQuery);
                    stmt.setString(1, user_to_update);
                    affected_rows = stmt.executeUpdate();
                    //check the update
                    if (affected_rows == 1) {
                        System.out.println(user_to_update + "was successfully verified!");
                    } else {
                        System.out.println("Error: user verification failed!");
                    }

                }
            } catch (Exception ex) {
                System.out.println("Update user failed: An Exception has occurred! " + ex);
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
        } else {
            System.out.println("unexpected - no users to verify");
        }

    }
}
