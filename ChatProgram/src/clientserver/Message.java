package clientserver;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private User sender;
    private ArrayList<User> recipients;
    private String text;
    private Icon icon;
    private String serverTimestamp;
    private String receivedTimestamp;


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

    public void setServerTimestamp(String serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    public void setReceivedTimestamp(String receivedTimestamp) {
        this.receivedTimestamp = receivedTimestamp;
    }

    public String getServerTimestamp() {
        return serverTimestamp;
    }

    public String getReceivedTimestamp() {
        return receivedTimestamp;
    }

    public String toString() {
        return String.format("%s: %s", sender.getName(), text);
    }

    public String getLogInfo() {
        String receivers = "[ ";
        for (User u : recipients) {
            receivers += u.getName() + " ";
        }
        receivers += "]";
        return String.format("Message sent from: %s -- recipients: %s -- content: %s -- sent: %s", sender.getName(), receivers, text, serverTimestamp);
    }

    public void setUsername(String username) {
        sender.setName(username);
    }

    public void setRecipients(ArrayList<User> recipients) {
        this.recipients = recipients;
    }
}
