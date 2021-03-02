package clientserver;

import javax.swing.*;
import java.io.Serializable;

public class Message implements Serializable {
    private String text;
    private Icon icon;

    public Message(String text, Icon icon){
        this.text=text;
        this.icon=icon;
    }

    public String getText() {
        return text;
    }

    public Icon getIcon() {
        return icon;
    }

    public String toString() {
        return String.format("%s", text);
    }
}
