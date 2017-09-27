package DAOs;

import Beans.MessageBean;
import Beans.SendMessageBean;
import DB.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MessageDAO {

    static Connection currentCon = null;
    static ResultSet rs = null;

    public static MessageBean returnMessages(MessageBean bean) {
        Statement stmt = null;
        ArrayList in_messages_array = new ArrayList();
        ArrayList out_messages_array = new ArrayList();

        ArrayList al1;
        ArrayList al2;
        String searchQuery;
        String username;
        String sender;
        String receiver;
        username = bean.getUsername();

        searchQuery = "select message,isseen,from_user,to_user,date,messageID from message where from_user = '" + username + "' or to_user = '" + username + "' ";

        try {
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();
            rs = stmt.executeQuery(searchQuery);

            while (rs.next()) {

                receiver = rs.getString("to_user");
                sender = rs.getString("from_user");
                if (Objects.equals(username, receiver)) {
                    al1 = new ArrayList();

                    al1.add(rs.getBoolean("isseen"));
                    al1.add(sender);
                    al1.add(rs.getString("message"));
                    al1.add(rs.getDate("date"));
                    al1.add(rs.getTime("date"));
                    al1.add(rs.getInt("messageID"));

                    in_messages_array.add(al1);
                } else {
                    al2 = new ArrayList();

                    al2.add(receiver);
                    al2.add(rs.getString("message"));
                    al2.add(rs.getDate("date"));
                    al2.add(rs.getTime("date"));
                    al2.add(rs.getInt("messageID"));

                    out_messages_array.add(al2);
                }

            }
            bean.setIn_messages(in_messages_array);
            bean.setOut_messages(out_messages_array);

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

    public static void insertMessage(SendMessageBean message) {

        int affected_rows;
        PreparedStatement stmt = null;
        String body = message.getMessage();
        String sender = message.getFrom_user();
        String receiver = message.getTo_user();
        Boolean isseen = message.getIsseen();
        int message_id = message.getMessage_id();
        String date = message.getDate();

        String insertQuery = "insert into message (messageID,message,from_user,to_user,isseen,date) values(?,?,?,?,?,?)";

        try {

            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.prepareStatement(insertQuery);
            stmt.setInt(1, message_id);
            stmt.setString(2, body);
            stmt.setString(3, sender);
            stmt.setString(4, receiver);
            stmt.setBoolean(5, isseen);
            stmt.setString(6, date);
            affected_rows = stmt.executeUpdate();

            if (affected_rows == 1) {
                System.out.println("the insertion succeed,the message was sent");
            } else {
                System.out.println("something went wrong");
            }

        } catch (Exception ex) {
            System.out.println("message send failed: An Exception has occurred! " + ex);
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

    }

    public static SendMessageBean seen(int message_id) {

        SendMessageBean bean = new SendMessageBean();
        
        PreparedStatement pstmt = null;
        Statement stmt = null;
        String updateQuery;
        String searchQuery;
        int affected_rows;

        updateQuery = " update message set isseen = 1 where messageID = ? and isseen = 0";
        searchQuery =" select message, from_user, date from message where messageID = '" + message_id + "'";
        
        try {
            currentCon = ConnectionManager.getConnection();
            pstmt = currentCon.prepareStatement(updateQuery);
            pstmt.setInt(1, message_id);
            affected_rows = pstmt.executeUpdate();

            if (affected_rows == 1) {
                System.out.println("okey update completed");
            } else {
                System.out.println("something went wrong");
            }
            
            stmt = currentCon.createStatement();
            rs = stmt.executeQuery(searchQuery);
            
            if(rs.next()){
                bean.setMessage(rs.getString("message"));
                bean.setFrom_user(rs.getString("from_user"));
                java.util.Date date;
                Timestamp timestamp = rs.getTimestamp("date");
                date = new java.util.Date(timestamp.getTime());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String reportDate = df.format(date);
                bean.setDate(reportDate);
            }

        } catch (Exception ex) {
            System.out.println("something failed: An Exception has occurred! " + ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
                rs = null;
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
                pstmt = null;
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
    
    public static int UnreadMessages(String username) {
        Statement stmt = null;
        String searchQuery;
        
        searchQuery = "select isseen from message where to_user = '" + username + "'";

        int counter = 0;
        
        try {
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();
            rs = stmt.executeQuery(searchQuery);
            
            while (rs.next()) {

                if (rs.getBoolean("isseen") == false) {
                    System.out.println("unread message!");
                    counter++;
                }

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

        return counter;

    }

}
