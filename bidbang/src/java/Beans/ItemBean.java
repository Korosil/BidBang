package Beans;

import java.io.InputStream;
import java.sql.JDBCType;
import java.util.Date;

public class ItemBean {

    private int itemID;
    private String name;
    private String category;
    private float currently;
    private float buy_price;
    private float first_bid;
    private int num_bids;
    private String seller_name;
    private String city;
    private String state;
    private String country;
    private float latitude;
    private float longtitude;
    private Date started_date;
    private String description;
    private String current_buyer_username;
    private String end_date;
    InputStream photo;

    static int counter = 13;

    public int getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public float getCurrently() {
        return currently;
    }

    public float getBuy_price() {
        return buy_price;
    }

    public float getFirst_bid() {
        return first_bid;
    }

    public int getNum_bids() {
        return num_bids;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public Date getStarted_date() {
        return started_date;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrent_buyer_username() {
        return current_buyer_username;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setItemID(int ID) {
        itemID = ID;
    }

    public void setItemID() {
        counter++;
        itemID = counter;
    }

    public void setName(String the_name) {
        name = the_name;
    }

    public void setCategory(String the_category) {
        category = the_category;
    }

    public void setCurrently(float the_currently) {
        currently = the_currently;
    }

    public void setBuy_price(float the_buy_price) {
        buy_price = the_buy_price;
    }

    public void setFirst_bid(float the_first_bid) {
        first_bid = the_first_bid;
    }

    public void setNum_bids(int the_num_of_bids) {
        num_bids = the_num_of_bids;
    }

    public void setSeller_name(String the_seller_name) {
        seller_name = the_seller_name;
    }

    public void setCity(String the_city) {
        city = the_city;
    }

    public void setState(String the_state) {
        state = the_state;
    }

    public void setCountry(String the_country) {
        country = the_country;
    }

    public void setLatitude(float the_latitude) {
        latitude = the_latitude;
    }

    public void setLongtitude(float the_longtitude) {
        longtitude = the_longtitude;
    }

    public void setStarted_date(Date the_started_date) {
        started_date = the_started_date;
    }

    public void setDescription(String the_description) {
        description = the_description;
    }

    public void setCurrent_buyer_username(String the_current_buyer_username) {
        current_buyer_username = the_current_buyer_username;
    }

    public void setEnd_date(String the_end_date) {
        end_date = the_end_date;
    }

}
