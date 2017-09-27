package Beans;

import java.sql.*;
import java.util.ArrayList;

public class SearchBean {
    private String search_item;
    private ArrayList search_rs;

    public String getSearch_item(){
        return search_item;
    }
    
    public ArrayList getSearch_rs(){
        return search_rs;
    }
    
    public void setSearch_item(String item_to_search){
        search_item = item_to_search;
    }
    
    public void setSearch_rs(ArrayList returned_search_rs){
        search_rs = returned_search_rs;
    }
        
}
