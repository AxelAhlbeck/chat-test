package gui;

import clientserver.Message;
import clientserver.User;

import javax.swing.*;
import java.awt.*;

public class UserRenderer extends JLabel implements ListCellRenderer<User> {
    @Override
    public Component getListCellRendererComponent(JList<? extends User> list, User user, int index, boolean isSelected, boolean cellHasFocus) {
        setIcon(user.getProfilePic());
        setText(user.getName());
        return this;
    }
}
