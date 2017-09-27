package Beans;

import java.util.Date;

public class SendMessageBean {

    private String message;
    private String from_user;
    private String to_user;
    private int message_id;
    private Boolean isseen;
    private String date;
    
    static int counter = 6;

    public String getMessage() {
        return message;
    }

    public void setMessage(String the_message) {
        message = the_message;
    }

    public String getFrom_user() {
        return from_user;
    }

    public void setFrom_user(String sender) {
        from_user = sender;
    }

    public String getTo_user() {
        return to_user;
    }

    public void setTo_user(String receiver) {
        to_user = receiver;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int ID) {
        message_id = ID;
    }
    
    public void setMessage_id() {
        counter++;
        message_id = counter;
    }

    public Boolean getIsseen() {
        return isseen;
    }

    public void setIsseen(Boolean flag) {
        isseen = flag;
    }
    
    public String getDate() {
        return date;
    }

    public void setDate(String newdate) {
        date = newdate;
    }

}
