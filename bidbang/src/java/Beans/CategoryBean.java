package Beans;

import java.util.ArrayList;

public class CategoryBean {
    private String category;
    private ArrayList category_rs;

    public String getCategory(){
        return category;
    }
    
    public ArrayList getCategory_rs(){
        return category_rs;
    }
    
    public void setCategory(String Category){
        category = Category;
    }
    
    public void setCategory_rs(ArrayList Category_rs){
        category_rs = Category_rs;
    }
}
