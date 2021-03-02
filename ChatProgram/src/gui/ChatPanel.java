package gui;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel {
    private JList list;
    private JScrollPane scrollPane;

    public ChatPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);

        list = new JList();
        scrollPane = new JScrollPane(list);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(width-10, height-30));
        add(scrollPane);
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    }

    public void updateListView(String[] infoStrings) {
        list.setListData(infoStrings);
    }
}
