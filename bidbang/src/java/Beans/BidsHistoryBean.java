package Beans;

import java.util.ArrayList;

public class BidsHistoryBean {

    private int auctionID;
    ArrayList thebids;

    public int get_auctionID() {
        return auctionID;
    }

    public void set_auctionID(int ID) {
        auctionID = ID;
    }

    public ArrayList get_thebids() {
        return thebids;
    }

    public void set_thebids(ArrayList bids) {
        thebids = bids;
    }
}
