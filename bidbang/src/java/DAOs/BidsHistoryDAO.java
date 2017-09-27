package DAOs;

import Beans.BidsHistoryBean;
import Beans.BidBean;
import DB.ConnectionManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BidsHistoryDAO {

    static Connection currentCon = null;
    static ResultSet rs = null;

    public static BidsHistoryBean returnHistory(BidsHistoryBean bean) {
        Statement stmt = null;
        BidBean bid = null;
        ArrayList history_array = new ArrayList();

        int itemID = bean.get_auctionID();
        System.out.println("Searching for item with ID = " + itemID + " ...");
        String SearchQuery = "select * from bids where auctionID = '" + itemID + "'";

        try {
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();
            rs = stmt.executeQuery(SearchQuery);

            while (rs.next()) {
                bid = new BidBean();

                bid.setAuctionID(itemID);
                bid.setUsername(rs.getString("username"));
                bid.setTime_of_bid(rs.getDate("time_of_bid"));
                bid.setAmount(rs.getFloat("amount"));

                history_array.add(bid);
            }
            bean.set_auctionID(itemID);
            bean.set_thebids(history_array);

        } catch (Exception ex) {
            System.out.println("failed: An Exception has occurred! " + ex);
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
