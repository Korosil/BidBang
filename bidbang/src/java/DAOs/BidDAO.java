package DAOs;

import Beans.DisplayAuctionsBean;
import Beans.BidBean;
import DB.ConnectionManager;
import static DAOs.UserDAO.rs;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class BidDAO {

    static Connection currentCon = null;
    static ResultSet rs = null;
    static ResultSet rs1 = null;
    static ResultSet rs3 = null;

    public static BidBean verifySetBid(BidBean bid_bean) {
        PreparedStatement stmt = null;
        PreparedStatement update_stmt = null;
        PreparedStatement next_stmt = null;

        int affected_rows;
        int insert_affected_rows;

        Date time_of_bid = bid_bean.getTime_of_bid();
        java.sql.Date sql_time_of_bid = new java.sql.Date(time_of_bid.getTime());

        int item_id = bid_bean.getAuctionID();
        Float user_bid_amount = bid_bean.getAmount();
        String bidder_username = bid_bean.getUsername();

        String next_searchQuery;

        String searchQuery = "select * from auction where itemID = ? and currently <  ? for update";

        String updateQuery = "update auction set currently = ? ,  num_bids = num_bids +1 , current_buyer_username = ? where itemID= ? and currently < ?";
        // execute the java preparedstatement
        try {
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.prepareStatement(searchQuery);
            stmt.setInt(1, item_id);
            stmt.setFloat(2, user_bid_amount);
            rs = stmt.executeQuery();
            if (rs.next()) {
                update_stmt = currentCon.prepareStatement(updateQuery);
                update_stmt.setFloat(1, user_bid_amount);
                update_stmt.setString(2, bidder_username);
                update_stmt.setInt(3, item_id);
                update_stmt.setFloat(4, user_bid_amount);
                affected_rows = update_stmt.executeUpdate();

                if (affected_rows == 1) {
                    System.out.println("congrats: bids almost completed ,update bids table");
                    next_searchQuery = "insert into bids (auctionID,time_of_bid,amount,username) values ( ?,?,?,?) ";
                    next_stmt = currentCon.prepareStatement(next_searchQuery);
                    next_stmt.setInt(1, item_id);
                    next_stmt.setDate(2, sql_time_of_bid);
                    next_stmt.setFloat(3, user_bid_amount);
                    next_stmt.setString(4, bidder_username);
                    insert_affected_rows = next_stmt.executeUpdate();

                    if (insert_affected_rows == 1) {
                        System.out.println("congrats:inserted in bids completed");
                        bid_bean.setVerify(1);
                    } else {
                        System.out.println("very unexpected error happened when inserting to bids table");
                    }
                }
            } else {
                System.out.println("bid failed:the amount is not valid or you are late :(");
                bid_bean.setVerify(0);
            }

        } catch (Exception ex) {
            System.out.println("bid failed: An Exception has occurred! " + ex);
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
            if (update_stmt != null) {
                try {
                    update_stmt.close();
                } catch (Exception e) {
                }
                update_stmt = null;
            }
            if (next_stmt != null) {
                try {
                    next_stmt.close();
                } catch (Exception e) {
                }
                next_stmt = null;
            }

            if (currentCon != null) {
                try {
                    currentCon.close();
                } catch (Exception e) {
                }

                currentCon = null;
            }
        }

        return bid_bean;

    }

    public static DisplayAuctionsBean DisplayAuctions(DisplayAuctionsBean bean) {

        int auction_id;
        PreparedStatement first_stmt = null;
        PreparedStatement second_stmt = null;
        PreparedStatement third_stmt = null;
        ArrayList auctions_array = new ArrayList();
        ArrayList seller_array = new ArrayList();
        String searchQuery_1;
        String searchQuery_2;
        String searchQuery_3;
        String current_buyer;
        ArrayList al1;
        ArrayList al3;
        String username = bean.getUsername();

        searchQuery_1 = "select distinct auctionID from bids where username ='" + username + "' ";

        try {
            currentCon = ConnectionManager.getConnection();
            first_stmt = currentCon.prepareStatement(searchQuery_1);
            rs = first_stmt.executeQuery();
            //so in rs we have the auctions that the user is active 

            while (rs.next()) {

                al1 = new ArrayList();

                auction_id = rs.getInt("auctionID");
                second_stmt = null;
                searchQuery_2 = "select itemID,name,currently,current_buyer_username,end_date from auction where itemID = ?";
                second_stmt = currentCon.prepareStatement(searchQuery_2);
                second_stmt.setInt(1, auction_id);
                rs1 = second_stmt.executeQuery();

                if (rs1.next()) {
                    al1.add(rs1.getInt("itemID"));
                    al1.add(rs1.getString("name"));
                    al1.add(rs1.getFloat("currently"));
                    al1.add(rs1.getString("current_buyer_username"));
                    al1.add(rs1.getDate("end_date"));
                }
                auctions_array.add(al1);

            }
            bean.setAuctions_array(auctions_array);

            searchQuery_3 = "select itemID,name,currently,current_buyer_username,end_date from auction where seller_name = ? ";
            third_stmt = currentCon.prepareStatement(searchQuery_3);
            third_stmt.setString(1, username);
            rs3 = third_stmt.executeQuery();

            while (rs3.next()) {
                al3 = new ArrayList();

                al3.add(rs3.getInt("itemID"));
                al3.add(rs3.getString("name"));
                al3.add(rs3.getFloat("currently"));
                al3.add(rs3.getString("current_buyer_username"));
                al3.add(rs3.getDate("end_date"));

                seller_array.add(al3);
            }
            bean.setSeller_auctions(seller_array);

        } catch (Exception ex) {
            System.out.println("Display auctions failed: An Exception has occurred! " + ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
                rs = null;
            }
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e) {
                }
                rs1 = null;
            }
            if (rs3 != null) {
                try {
                    rs3.close();
                } catch (Exception e) {
                }
                rs3 = null;
            }

            if (first_stmt != null) {
                try {
                    first_stmt.close();
                } catch (Exception e) {
                }
                first_stmt = null;
            }
            if (second_stmt != null) {
                try {
                    second_stmt.close();
                } catch (Exception e) {
                }
                second_stmt = null;
            }
            if (third_stmt != null) {
                try {
                    third_stmt.close();
                } catch (Exception e) {
                }
                third_stmt = null;
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

    public static Boolean deleteAuction(int itemID) {
        Boolean deleted = false;
        String searchQuery;
        String deleteQuery;
        int number_of_bids = 0;
        int affected_rows;
        PreparedStatement s_stmt = null;
        PreparedStatement d_stmt = null;

        searchQuery = " select num_bids from auction where itemID = ? ";

        try {
            currentCon = ConnectionManager.getConnection();
            s_stmt = currentCon.prepareStatement(searchQuery);
            s_stmt.setInt(1, itemID);
            rs = s_stmt.executeQuery();
            if (rs.next()) {
                number_of_bids = rs.getInt("num_bids");
            } else {
                System.out.println("something went wrong,unexpected");
            }

            if (number_of_bids == 0) {
                deleteQuery = "delete from auction where itemID = ? and num_bids = 0";
                d_stmt = currentCon.prepareStatement(deleteQuery);
                d_stmt.setInt(1, itemID);
                affected_rows = d_stmt.executeUpdate();

                if (affected_rows == 1) {
                    System.out.println("everything okay auction deleted");
                    deleted = true;
                } else {
                    System.out.println("something went wrong,unexpected");
                }
            } else {
                System.out.println("a bid has been made for this item you cant delete the auction");
                deleted = false;
            }

        } catch (Exception ex) {
            System.out.println("DeleteItem failed: An Exception has occurred! " + ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (s_stmt != null) {
                try {
                    s_stmt.close();
                } catch (Exception e) {
                }
                s_stmt = null;
            }
            if (d_stmt != null) {
                try {
                    d_stmt.close();
                } catch (Exception e) {
                }
                d_stmt = null;
            }
            if (currentCon != null) {
                try {
                    currentCon.close();
                } catch (Exception e) {
                }

                currentCon = null;
            }

        }

        return deleted;
    }

}
