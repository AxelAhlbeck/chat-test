package gui;

import controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextBoxPanel extends JPanel implements ActionListener {
    private JTextField textField;
    private JButton sendButton;
    private JButton sendAttachment;
    private JButton addContactButton;
    private JButton disconnectButton;
    private ClientController controller;
    private JFileChooser chooser;

    public TextBoxPanel(ClientController controller, int width, int height) {
        this.controller = controller;
        chooser = new JFileChooser(System.getProperty("user.dir"));
        //setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(width/3, height-30));
        add(textField, BorderLayout.WEST);

        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        add(sendButton, BorderLayout.EAST);
        sendAttachment = new JButton("Send Attachment");
        sendAttachment.addActionListener(this);
        add(sendAttachment);

        addContactButton = new JButton("Add to Contacts");
        addContactButton.addActionListener(this);
        add(addContactButton, BorderLayout.EAST);

        disconnectButton = new JButton("Disconnect");
        disconnectButton.addActionListener(this);
        add(disconnectButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            String text = textField.getText();
            if(text != null && !text.isEmpty()) {
                controller.send(text);
            }
        }
        if (e.getSource() == sendAttachment) {
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                ImageIcon imageIcon = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
                controller.send(imageIcon, textField.getText());
            }
        }
        if (e.getSource() == addContactButton) {
            controller.addContacts();
        }
        if (e.getSource() == disconnectButton) {
            controller.closeConnection();
        }
    }
}
