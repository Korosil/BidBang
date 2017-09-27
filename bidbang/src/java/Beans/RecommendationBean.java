package Beans;

import java.util.ArrayList;
import java.util.Date;

public class RecommendationBean {

    private ArrayList<String> recommended_items;
    private ArrayList<Float> currently_prices;
    private ArrayList<Date> end_dates;
    private ArrayList<Integer> itemIDs;
    private ArrayList<String> itemCountries;

    public ArrayList<String> getRecommended_items() {
        return recommended_items;
    }

    public void setRecommended_items(ArrayList<String> recommended_array) {
        recommended_items = recommended_array;
    }

    public ArrayList<Float> getCurrently_prices() {
        return currently_prices;
    }

    public void setCurrently_prices(ArrayList<Float> currently_array) {
        currently_prices = currently_array;
    }

    public ArrayList<Date> getEnd_dates() {
        return end_dates;
    }

    public void setEnd_dates(ArrayList<Date> end_dates_array) {
        end_dates = end_dates_array;
    }

    public ArrayList<Integer> getRecommended_itemIDs() {
        return itemIDs;
    }

    public void setRecommended_itemIDs(ArrayList<Integer> recommended_itemIDs) {
        itemIDs = recommended_itemIDs;
    }
    
    public ArrayList<String> getitemCountries() {
        return itemCountries;
    }

    public void setitemCountries(ArrayList<String> recommended_itemCountries) {
        itemCountries = recommended_itemCountries;
    }

}
