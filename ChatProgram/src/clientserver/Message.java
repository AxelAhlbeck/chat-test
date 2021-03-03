package clientserver;

import javax.swing.*;
import java.io.Serializable;

public class Message implements Serializable {
    private String text;
    private Icon icon;
    private String username;

    public Message(String username, String text, Icon icon){
        this.text=text;
        this.icon=icon;
        this.username = username;
    }

    public String getUsername() { return username; }

    public String getText() {
        return text;
    }

    public Icon getIcon() {
        return icon;
    }

    public String toString() {
        return String.format("%s: %s", username, text);
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
