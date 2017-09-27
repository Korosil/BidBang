package Beans;

import java.util.ArrayList;

public class MessageBean {

    String username;
    ArrayList in_messages;
    ArrayList out_messages;

    public String getUsername() {
        return username;
    }

    public void setUsername(String user_name) {
        username = user_name;
    }

    public ArrayList getIn_messages() {
        return in_messages;
    }

    public void setIn_messages(ArrayList in_mess) {
        in_messages = in_mess;
    }

    public ArrayList getOut_messages() {
        return out_messages;
    }

    public void setOut_messages(ArrayList out_mess) {
        out_messages = out_mess;
    }

}
