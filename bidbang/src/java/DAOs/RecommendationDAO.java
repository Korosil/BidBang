package DAOs;

import Beans.RecommendationBean;
import DB.ConnectionManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class RecommendationDAO {

    public static Connection currentCon = null;
    public static ResultSet mybids = null;
    public static ResultSet mutuals = null;
    public static ResultSet finalres = null;
    static ResultSet currentuserbids = null;
    static ResultSet resultset1 = null;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object[] mutuality(String username) {

        int my_items_id;
        String mutual_username;
        Object[] a = null;

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        ArrayList<Integer> myArrayBid = new ArrayList<Integer>();

        mybids = itemsOfUser(username);//in mybids we have the result set of the items the user have bidded
        try {
            if (!mybids.next())//no bids made
            {
                System.out.println(" there are no bids ");
                return null;
            } else//there are bids continue
            {
                mybids.beforeFirst();
                System.out.println("hakuna matata");
            }

            while (mybids.next()) {

                my_items_id = mybids.getInt("auctionID");
                myArrayBid.add(my_items_id);
                mutuals = mutualUsers(my_items_id, username);//in this result set we have the users that bidded for the item with itemID = my_items_id
                if (!mutuals.next()) {
                    System.out.println(" no mutual users ");
                    return null;
                } else {
                    mutuals.beforeFirst();
                }
                
                while (mutuals.next()) {
                    mutual_username = mutuals.getString("username");
                    if (!map.containsKey(mutual_username)) {
                        map.put(mutual_username, 1);	//an o xristis me auto to user_id den iparxei sto hashmap mas tote balton me kleidi to id tou kai value to 1
                    } else {
                        map.put(mutual_username, map.get(mutual_username) + 1);
                    }

                }

            }
            a = map.entrySet().toArray();
            Arrays.sort(a, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    return ((Map.Entry<String, Integer>) o2).getValue().compareTo(
                            ((Map.Entry<String, Integer>) o1).getValue());
                }
            });

        } catch (Exception ex) {
            System.out.println(" An Exception has occurred! " + ex);
            ex.printStackTrace();
        }
        return a;

    }

    public static ResultSet itemsOfUser(String username) {

        PreparedStatement stmt = null;
        ResultSet result = null;
        String searchQuery = "select distinct auctionID from bids where username = ?";
        try {
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.prepareStatement(searchQuery);
            stmt.setString(1, username);
            result = stmt.executeQuery();

        } catch (Exception ex) {
            System.out.println("something failed: An Exception has occurred! " + ex);
        } finally {
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (Exception e) {
//                }
//                stmt = null;
//            }
//            if (currentCon != null) {
//                try {
//                    currentCon.close();
//                } catch (Exception e) {
//                }
//
//                currentCon = null;
//            }
        }
        return result;
    }

    public static ResultSet mutualUsers(int my_items_id, String username) {
        PreparedStatement stmt = null;
        ResultSet result = null;
        String searchQuery = "select distinct username from bids where auctionID = ? and username != ?";
        try {
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.prepareStatement(searchQuery);
            stmt.setInt(1, my_items_id);
            stmt.setString(2, username);
            result = stmt.executeQuery();

        } catch (Exception ex) {
            System.out.println("something failed: An Exception has occurred! " + ex);
        } finally {
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (Exception e) {
//                }
//                stmt = null;
//            }
//            if (currentCon != null) {
//                try {
//                    currentCon.close();
//                } catch (Exception e) {
//                }
//
//                currentCon = null;
//            }
        }
        return result;

    }

    public static ArrayList<Integer> recommended_items(Object[] a, String username) {
        PreparedStatement stmt = null;
        ResultSet result = null;
        int k = 3;
        int counter = 0;
        int recID;
        ArrayList<Integer> myArrayBid = new ArrayList<Integer>();
        int myitemsID;
        ArrayList<Integer> recommended = new ArrayList<Integer>();

        try {
            currentuserbids = itemsOfUser(username);
            while (currentuserbids.next()) {
                myitemsID = currentuserbids.getInt("auctionID");
                myArrayBid.add(myitemsID);
            }

            for (Object e : a) {
                if (k == counter) {
                    break;
                }
                finalres = itemsOfUser(((Map.Entry<String, Integer>) e).getKey());
                while (finalres.next()) {
                    recID = finalres.getInt("auctionID");
                    if (!myArrayBid.contains(recID) && !recommended.contains(recID)) {
                        recommended.add(recID);
                    }
                }
                counter++;
            }
        } catch (Exception ex) {
            System.out.println("something failed: An Exception has occurred! " + ex);
        } finally {
            if (currentuserbids != null) {
                try {
                    currentuserbids.close();
                } catch (Exception e) {
                }
                currentuserbids = null;
            }
        }

        return recommended;

    }

    public static RecommendationBean returnInfos(ArrayList<Integer> recommended) {
        ArrayList<Integer> al0 = new ArrayList<Integer>();
        ArrayList<String> al1 = new ArrayList<String>();
        ArrayList<Float> al2 = new ArrayList<Float>();
        ArrayList<Date> al3 = new ArrayList<Date>();
        ArrayList<String> al4 = new ArrayList<String>();
        Statement stmt = null;

        String searchQuery;
        int i;
        Boolean more;

        RecommendationBean recommendation_bean = new RecommendationBean();
        try {
            for (i = 0; i < recommended.size(); i++) {
                searchQuery = "select itemID,name,currently,end_date,country from auction where itemID = '" + recommended.get(i) + "' ";

                currentCon = ConnectionManager.getConnection();
                stmt = currentCon.createStatement();
                resultset1 = stmt.executeQuery(searchQuery);
                more = resultset1.next();
                al0.add(resultset1.getInt("itemID"));
                al1.add(resultset1.getString("name"));
                al2.add(resultset1.getFloat("currently"));
                al3.add(resultset1.getDate("end_date"));
                al4.add(resultset1.getString("country"));

                if (resultset1 != null) {
                    try {
                        resultset1.close();
                    } catch (Exception e) {
                    }
                    resultset1 = null;
                }

            }
            recommendation_bean.setRecommended_itemIDs(al0);
            recommendation_bean.setRecommended_items(al1);
            recommendation_bean.setCurrently_prices(al2);
            recommendation_bean.setEnd_dates(al3);
            recommendation_bean.setitemCountries(al4);

        } catch (Exception ex) {
            System.out.println("Log In failed: An Exception has occurred! " + ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
                stmt = null;
            }

//            if (currentCon != null) {
//                try {
//                    currentCon.close();
//                } catch (Exception e) {
//                }
//
//                currentCon = null;
//            }
        }
        return recommendation_bean;

    }

}
