package gui;

import controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextBoxPanel extends JPanel implements ActionListener {
    private JTextField textField;
    private JButton sendButton;
    private ClientController controller;

    public TextBoxPanel(ClientController controller, int width, int height) {
        this.controller = controller;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(2*width/3, height-10));
        add(textField, BorderLayout.WEST);

        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        add(sendButton, BorderLayout.EAST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            String text = textField.getText();
            if(text != null && !text.isEmpty()) {
                controller.send(text);
            }
        }
    }
}
