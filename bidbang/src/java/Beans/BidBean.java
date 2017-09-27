package Beans;

import java.util.Date;

public class BidBean {

    private int auctionID;
    private Date time_of_bid;
    private float amount;
    private String username;
    private int verify;

    public int getAuctionID() {
        return auctionID;
    }

    public Date getTime_of_bid() {
        return time_of_bid;
    }

    public float getAmount() {
        return amount;
    }

    public String getUsername() {
        return username;
    }

    public int getVerify() {
        return verify;
    }

    public void setAuctionID(int auction_id) {
        auctionID = auction_id;
    }

    public void setTime_of_bid(Date the_time_of_bid) {
        time_of_bid = the_time_of_bid;
    }

    public void setAmount(float the_amount) {
        amount = the_amount;
    }

    public void setUsername(String the_username) {
        username = the_username;
    }

    public void setVerify(int the_verify) {
        verify = the_verify;
    }
}
