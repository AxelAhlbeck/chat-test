package gui;

import clientserver.Message;
import clientserver.User;

import javax.swing.*;
import java.awt.*;


public class ListPanel extends JPanel {
    private JList list;
    private JScrollPane scrollPane;

    public ListPanel(int width, int height, String desc) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);

        JLabel descLabel = new JLabel(desc);
        descLabel.setBounds(0, 0, width, 60);
        descLabel.setForeground(Color.WHITE);
        add(descLabel);
        list = new JList();

        scrollPane = new JScrollPane(list);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(width-10, height-60));
        add(scrollPane);
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    }

    public void updateListView(Message[] messages) {
        list.setListData(messages);
    }

    public void setListRenderer(ListCellRenderer renderer) {
        list.setCellRenderer(renderer);
    }

    public void updateListView(User[] users) {
        list.setListData(users);
    }
}
