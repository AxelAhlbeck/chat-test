package gui;

import clientserver.Message;
import controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    TextBoxPanel textBoxPanel;
    ListPanel chatPanel;
    ListPanel contactsPanel;
    ListPanel onlinePanel;

    public GUI(ClientController controller, int width, int height) {

        setPreferredSize(new Dimension(width, height));

        textBoxPanel = new TextBoxPanel(controller, width, height / 5);
        add(textBoxPanel, BorderLayout.SOUTH);

        contactsPanel = new ListPanel(width / 3, 4 * height / 5, "Contacts:");
        contactsPanel.setListRenderer(new UserRenderer());
        add(contactsPanel, BorderLayout.WEST);

        chatPanel = new ListPanel( width / 3, 4 * height / 5, "Chatroom:");
        chatPanel.setListRenderer(new MessageRenderer());
        add(chatPanel, BorderLayout.CENTER);

        onlinePanel = new ListPanel(width / 3, 4 * height / 5, "Online Users:");
        onlinePanel.setListRenderer(new UserRenderer());
        add(onlinePanel, BorderLayout.EAST);




        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public GUI(ClientController controller) {
        this(controller, 1000, 500);
    }

    public GUI() {
        this(null, 500, 500);
    }

    public static void main(String[] args) {
        new GUI();
    }

    public <T> void updateListView(T[] messages) {
        chatPanel.updateListView(messages);
    }
}
