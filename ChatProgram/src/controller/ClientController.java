package controller;

import clientserver.Callback;
import clientserver.ChatClient;
import clientserver.Message;
import clientserver.User;
import gui.GUI;

import javax.swing.*;
import java.util.List;


public class ClientController implements Callback {
    private ChatClient client;
    private GUI gui;

    public ClientController(ChatClient client) {
        this.client = client;
        client.addMessageListener(this);
    }

    public ClientController() {
        this(new ChatClient());
    }

    public void connect(String username, ImageIcon profilePic, String ip, int port) {
        User user = new User(username, profilePic);
        client.setUser(user);
        client.connect(ip, port);
        gui = new GUI(this);
    }


    public void send(String text) {
        client.send(text);
    }

    @Override
    public void updateListView(Message[] messages) {
        gui.updateListView(messages);
    }
}
