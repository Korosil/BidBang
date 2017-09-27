package Beans;

public class PhotoBean {
    
    private int itemID;
    byte[] content;
    
    public int getItemID() {
        return itemID;
    }
    
    public void setItemID(int item_ID) {
        itemID = item_ID;
    }
    
    public byte[] getPhoto() {
        return content;
    }
    
    public void setPhoto(byte[] Photo) {
        content = Photo;
    }
    
}
