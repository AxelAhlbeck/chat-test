package gui;

import clientserver.Message;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChatPanel extends JPanel {
    private JList<Message> list;
    private JScrollPane scrollPane;

    public ChatPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);

        list = new JList<>();

        scrollPane = new JScrollPane(list);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(width-10, height-30));
        add(scrollPane);
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new MessageRenderer());
    }

    public void updateListView(Message[] messages) {
        list.setListData(messages);

    }
}
