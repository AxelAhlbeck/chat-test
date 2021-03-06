package gui;

import clientserver.Message;

import javax.swing.*;
import java.awt.*;

public class MessageRenderer extends JLabel implements ListCellRenderer<Message>{
    @Override
    public Component getListCellRendererComponent(JList<? extends Message> list, Message message, int index, boolean isSelected, boolean cellHasFocus) {
        setIcon(message.getIcon());
        setText(message.toString());
        return this;
    }
}
