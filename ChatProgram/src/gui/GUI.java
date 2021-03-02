package gui;

import clientserver.ChatClient;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private ChatClient client;
    private int width, height;
    TextBoxPanel textBoxPanel;
    ChatPanel chatPanel;

    public GUI(ChatClient client, int width, int height) {
        this.client = client;
        this.width = width;

        setPreferredSize(new Dimension(width, height));

        textBoxPanel = new TextBoxPanel(width, height/5);
        add(textBoxPanel, BorderLayout.SOUTH);

        chatPanel = new ChatPanel(width, 4 * height/5);
        add(chatPanel, BorderLayout.NORTH);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public GUI() {
        this(null, 500, 500);
    }

    public static void main(String[] args) {
        new GUI();
    }

}
