package clientserver;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private User sender;
    private ArrayList<User> recipients;
    private String text;
    private Icon icon;


    public Message(User sender, ArrayList<User> recipients, String text, Icon icon){
        this.sender = sender;
        this.recipients = recipients;
        this.text=text;
        this.icon=icon;
    }

    public User getSender() { return sender; }

    public ArrayList<User> getRecipients() { return recipients; }

    public String getText() {
        return text;
    }

    public Icon getIcon() {
        return icon;
    }

    public String toString() {
        return String.format("%s: %s", sender.getName(), text);
    }

    public void setUsername(String username) {
        sender.setName(username);
    }

}
