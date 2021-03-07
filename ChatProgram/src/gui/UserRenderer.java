package gui;

import clientserver.User;

import javax.swing.*;
import java.awt.*;

public class UserRenderer extends JLabel implements ListCellRenderer<User> {

    public UserRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends User> list, User user, int index, boolean isSelected, boolean cellHasFocus) {
        setIcon(user.getProfilePic());
        setText(user.getName());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
