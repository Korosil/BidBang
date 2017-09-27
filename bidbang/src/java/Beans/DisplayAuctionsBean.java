package Beans;

import java.util.ArrayList;

public class DisplayAuctionsBean {

    String username;
    ArrayList auctions_array;
    ArrayList seller_auctions;

    public String getUsername() {
        return username;
    }

    public void setUsername(String user_name) {
        username = user_name;
    }

    public ArrayList getAuctions_array() {
        return auctions_array;
    }

    public void setAuctions_array(ArrayList auctions) {
        auctions_array = auctions;
    }

    public ArrayList getSeller_auctions() {
        return seller_auctions;
    }

    public void setSeller_auctions(ArrayList seller) {
        seller_auctions = seller;
    }

}
