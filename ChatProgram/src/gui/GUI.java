package gui;

import clientserver.Message;
import controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    TextBoxPanel textBoxPanel;
    ChatPanel chatPanel;

    public GUI(ClientController controller, int width, int height) {

        setPreferredSize(new Dimension(width, height));

        textBoxPanel = new TextBoxPanel(controller, width, height/5);
        add(textBoxPanel, BorderLayout.SOUTH);

        chatPanel = new ChatPanel(2 * width/3, 4 * height/5);
        add(chatPanel, BorderLayout.WEST);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public GUI(ClientController controller) {
        this(controller, 500, 500);
    }

    public GUI() {
        this(null, 500, 500);
    }

    public static void main(String[] args) {
        new GUI();
    }

    public void updateListView(Message[] messages) {
        chatPanel.updateListView(messages);
    }
}
