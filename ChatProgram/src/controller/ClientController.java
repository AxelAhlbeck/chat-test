package controller;

import clientserver.Callback;
import clientserver.ChatClient;
import clientserver.Message;
import clientserver.User;
import gui.GUI;

import javax.swing.*;


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
        gui = new GUI(this);
        User user = new User(username, profilePic);
        client.setUser(user);
        client.connect(ip, port);
    }

    public void send(String text) {
        client.send(text);
    }

    @Override
    public void updateListView(Message[] messages) {
        gui.updateListView(messages);
    }

    @Override
    public void updateListView(User[] users) {
        gui.updateListView(users);
    }

    public void addContacts() {
        User user = gui.getSelectedOnlineUser();
        Object[] contacts = gui.getContacts();
        User[] newContacts = new User[contacts.length+1];
        for (int i = 0; i < contacts.length; i++) {
            newContacts[i] = (User) contacts[i];
        }
        newContacts[newContacts.length-1] = user;
        gui.updateContacts(newContacts);
    }
}
