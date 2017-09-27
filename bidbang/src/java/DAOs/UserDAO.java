package DAOs;

import Beans.UserBean;
import DB.ConnectionManager;
import java.text.*;
import java.util.*;
import java.sql.*;

public class UserDAO {

    static Connection currentCon = null;
    static ResultSet rs = null;

    public static UserBean login(UserBean bean) {

        //preparing some objects for connection 
        Statement stmt = null;

        String username = bean.getUsername();
        String password = bean.getPassword();

        String searchQuery
                = "select * from user where username='"
                + username
                + "' AND password='"
                + password
                + "'";

        // "System.out.println" prints in the console; Normally used to trace the process
        System.out.println("Your user name is " + username);
        System.out.println("Your password is " + password);
        System.out.println("Query: " + searchQuery);

        try {
            //connect to DB 
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();
            rs = stmt.executeQuery(searchQuery);
            boolean more = rs.next();

            // if user does not exist set the isValid variable to false
            if (!more) {
                System.out.println("Sorry, you are not a registered user! Please sign up first");
                bean.setValid(false);
            } //if user exists set the isValid variable to true
            else if (more) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                Boolean verified = rs.getBoolean("verified");

                System.out.println("Welcome " + name);
                bean.setName(name);
                bean.setSurname(surname);
                bean.setVerified(verified);
                bean.setValid(true);
            }
        } catch (Exception ex) {
            System.out.println("Log In failed: An Exception has occurred! " + ex);
        } //some exception handling
        finally {
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

    public static UserBean signup(UserBean bean) {

        //preparing some objects for connection 
        Statement stmt = null;

        String username = bean.getUsername();
        String email = bean.getEmail();

        String searchQuery
                = "select * from user where username='"
                + username
                + "' OR email='"
                + email
                + "'";

        // "System.out.println" prints in the console; Normally used to trace the process
        System.out.println("Searching if username " + username + " is available");
        System.out.println("Searching if email " + email + " is available");
        System.out.println("Query: " + searchQuery);

        try {
            //connect to DB 
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();
            rs = stmt.executeQuery(searchQuery);
            boolean more = rs.next();

            // if username and email are available
            if (!more) {
                System.out.println("Username and email are available");
                String insertQuery
                        = "insert into user (username, password, name, surname, email, phonenumber, address, city, state, country, ZIPcode, TRN, verified)"
                        + "values ('"
                        + bean.getUsername()
                        + "','"
                        + bean.getPassword()
                        + "','"
                        + bean.getName()
                        + "','"
                        + bean.getSurname()
                        + "','"
                        + bean.getEmail()
                        + "','"
                        + bean.getPhonenumber()
                        + "','"
                        + bean.getAddress()
                        + "','"
                        + bean.getCity()
                        + "','"
                        + bean.getState()
                        + "','"
                        + bean.getCountry()
                        + "','"
                        + bean.getZipcode()
                        + "','"
                        + bean.getTrn()
                        + "','"
                        + 0
                        + "');";
                stmt.executeUpdate(insertQuery);
                System.out.println("Insert into database successful");
                bean.setValid(true);

            } //if username or email are not available
            else if (more) {
                System.out.println("Username or email are not available");
                bean.setValid(false);
            }
        } catch (Exception ex) {
            System.out.println("Signup failed: An Exception has occurred! " + ex);
        } //some exception handling
        finally {
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

    public static UserBean displayUser(UserBean bean) {

        Statement stmt = null;
        String username = bean.getUsername();

        String searchQuery = "select * from user where username = '" + username + "' ";

        try {
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();
            rs = stmt.executeQuery(searchQuery);

            if (rs.next()) {
                bean.setPassword("not displayed");
                bean.setName(rs.getString("name"));
                bean.setSurname(rs.getString("surname"));
                bean.setEmail(rs.getString("email"));
                bean.setPhonenumber(rs.getString("phonenumber"));
                bean.setAddress(rs.getString("address"));
                bean.setZipcode(rs.getString("ZIPcode"));
                bean.setCity(rs.getString("city"));
                bean.setState(rs.getString("state"));
                bean.setCountry(rs.getString("country"));
                bean.setTrn(rs.getString("TRN"));
                bean.setVerified(rs.getBoolean("verified"));
                bean.setRating(rs.getFloat("rating"));
                bean.setRCounter(rs.getInt("rcounter"));

            } else {
                System.out.println("unexpected error user not found");
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

    public static void rateUser(Float rating, String username) {

        PreparedStatement s_stmt = null;
        PreparedStatement u_stmt = null;
        String searchQuery;
        String updateQuery;
        int new_count;
        Float new_rating;

        int affected_rows;

        searchQuery = "select rcounter,rating from user where username = ? ";

        try {
            currentCon = ConnectionManager.getConnection();
            s_stmt = currentCon.prepareStatement(searchQuery);
            s_stmt.setString(1, username);
            rs = s_stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("unexpected error");
            } else {
                new_rating = rs.getFloat("rating") + rating;
                new_count = rs.getInt("rcounter") + 1;
                updateQuery = "update user set rating = ?, rcounter = ? where username = ?";
                u_stmt = currentCon.prepareStatement(updateQuery);
                u_stmt.setFloat(1, new_rating);
                u_stmt.setInt(2, new_count);
                u_stmt.setString(3, username);
                affected_rows = u_stmt.executeUpdate();

                if (affected_rows == 1) {
                    System.out.println("okay rate was set");
                } else {
                    System.out.println("something wrong happened");
                }
            }

        } catch (Exception ex) {
            System.out.println("rating failed: An Exception has occurred! " + ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
                rs = null;
            }

            if (s_stmt != null) {
                try {
                    s_stmt.close();
                } catch (Exception e) {
                }
                s_stmt = null;
            }
            if (u_stmt != null) {
                try {
                    u_stmt.close();
                } catch (Exception e) {
                }
                u_stmt = null;
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

    public static Float DisplayRating(String username) {
        PreparedStatement stmt = null;
        String searchQuery;
        int rcounter;
        Float rating;
        Float rating_to_display = 0.0f;

        searchQuery = "select rcounter,rating from user where username = ?";

        try {
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.prepareStatement(searchQuery);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("something went wrong");
            } else {
                rcounter = rs.getInt("rcounter");
                rating = rs.getFloat("rating");

                rating_to_display = rating / rcounter;
            }

        } catch (Exception ex) {
            System.out.println("rating display failed: An Exception has occurred! " + ex);
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
        return rating_to_display;

    }

}
