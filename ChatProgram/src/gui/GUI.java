package gui;

import clientserver.Callback;
import clientserver.ChatClient;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame implements Callback {
    TextBoxPanel textBoxPanel;
    ChatPanel chatPanel;

    public GUI(ChatClient client, int width, int height) {

        setPreferredSize(new Dimension(width, height));

        textBoxPanel = new TextBoxPanel(client, width, height/5);
        add(textBoxPanel, BorderLayout.SOUTH);

        chatPanel = new ChatPanel(width, 4 * height/5);
        add(chatPanel, BorderLayout.NORTH);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        client.addMessageListener(this);
    }

    public GUI(ChatClient client) {
        this(client, 500, 500);
    }

    public GUI() {
        this(null, 500, 500);
    }

    public static void main(String[] args) {
        new GUI();
    }

    @Override
    public void updateListView(String[] infoStrings) {
        chatPanel.updateListView(infoStrings);
    }
}
