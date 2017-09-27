package DAOs;

import Beans.ItemBean;
import Beans.PhotoBean;
import DB.ConnectionManager;
import static DAOs.BidDAO.currentCon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class ItemDAO {

    static Connection currentCon = null;
    static ResultSet rs = null;

    public static ItemBean returnItemObject(ItemBean bean) {
        //preparing some objects for connection 
        Statement stmt = null;

        int itemID = bean.getItemID();

        String searchQuery = "select * from auction where itemID = '" + itemID + "'";
        System.out.println(searchQuery);

        try {
            //connect to DB 
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();
            rs = stmt.executeQuery(searchQuery);
            boolean more = rs.next();

            if (more) {
                bean.setItemID(rs.getInt("itemID"));
                bean.setName(rs.getString("name"));
                bean.setCategory(rs.getString("category"));
                bean.setCurrently(rs.getFloat("currently"));
                bean.setBuy_price(rs.getFloat("buy_price"));
                bean.setFirst_bid(rs.getFloat("first_bid"));
                bean.setNum_bids(rs.getInt("num_bids"));
                bean.setSeller_name(rs.getString("seller_name"));
                bean.setCity(rs.getString("city"));
                bean.setState(rs.getString("state"));
                bean.setCountry(rs.getString("country"));
                bean.setLatitude(rs.getFloat("latitude"));
                bean.setLongtitude(rs.getFloat("longtitude"));
                bean.setStarted_date(rs.getDate("started_date"));
                bean.setDescription(rs.getString("description"));
                bean.setCurrent_buyer_username(rs.getString("current_buyer_username"));
                bean.setEnd_date(rs.getString("end_date"));

            } else {
                System.out.println("unexpected error occured item not found");
            }

        } catch (Exception ex) {
            System.out.println("DisplayItem failed: An Exception has occurred! " + ex);
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

    public static ItemBean insertItemObject(ItemBean item, PhotoBean photo) {
        Statement stmt = null;
        PreparedStatement pstmt = null;
        String name = item.getSeller_name();

        Date started_date = item.getStarted_date();
        java.sql.Date sql_started_date = new java.sql.Date(started_date.getTime());
        
//        Date end_date = item.getEnd_date();
//        java.sql.Date sql_end_date = new java.sql.Date(end_date.getTime());
        
        try {
            //connect to DB 
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();

            float buy_price = item.getBuy_price();
            
            String insertQuery = null;
            
            if (buy_price == 0) {

                insertQuery
                        = "insert into auction (itemID,name,category,currently,first_bid,num_bids,seller_name,city,state,country,latitude,longtitude,started_date,end_date,description)"
                        + "values ('"
                        + item.getItemID()
                        + "','"
                        + item.getName()
                        + "','"
                        + item.getCategory()
                        + "','"
                        + item.getCurrently()
                        + "','"
                        + item.getFirst_bid()
                        + "','"
                        + item.getNum_bids()
                        + "','"
                        + item.getSeller_name()
                        + "','"
                        + item.getCity()
                        + "','"
                        + item.getState()
                        + "','"
                        + item.getCountry()
                        + "','"
                        + item.getLatitude()
                        + "','"
                        + item.getLongtitude()
                        + "','"
                        + sql_started_date
                        + "','"
                        + item.getEnd_date()
                        + "','"
                        + item.getDescription()

                        + "');";
            } else {
                
                insertQuery
                        = "insert into auction (itemID,name,category,currently,buy_price,first_bid,num_bids,seller_name,city,state,country,latitude,longtitude,started_date,end_date,description)"
                        + "values ('"
                        + item.getItemID()
                        + "','"
                        + item.getName()
                        + "','"
                        + item.getCategory()
                        + "','"
                        + item.getCurrently()
                        + "','"
                        + buy_price
                        + "','"
                        + item.getFirst_bid()
                        + "','"
                        + item.getNum_bids()
                        + "','"
                        + item.getSeller_name()
                        + "','"
                        + item.getCity()
                        + "','"
                        + item.getState()
                        + "','"
                        + item.getCountry()
                        + "','"
                        + item.getLatitude()
                        + "','"
                        + item.getLongtitude()
                        + "','"
                        + sql_started_date
                        + "','"
                        + item.getEnd_date()
                        + "','"
                        + item.getDescription()
                        + "');";
            }

            stmt.executeUpdate(insertQuery);
            System.out.println("Insert into database successful");
            
            String insertPhotoQuery = null;
            if (photo.getPhoto() != null) {
                // fetches input stream of the upload file for the blob column
                insertPhotoQuery = "insert into photo (itemID,picture) values ( ?,?) ";
            }
            
            pstmt = currentCon.prepareStatement(insertPhotoQuery);
            pstmt.setInt(1, photo.getItemID());
            pstmt.setBytes(2, photo.getPhoto());
            
            pstmt.executeUpdate();
            System.out.println("Photo: Insert into database successful");

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

        return item;
    }
}
