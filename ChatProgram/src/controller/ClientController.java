package controller;

import clientserver.Callback;
import clientserver.ChatClient;
import clientserver.Message;
import clientserver.User;
import gui.GUI;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ClientController implements Callback {
    private ChatClient client;
    private GUI gui;
    private User user;

    public ClientController(ChatClient client) {
        this.client = client;
        client.addMessageListener(this);
    }

    public ClientController() {
        this(new ChatClient());
    }

    public void connect(String username, ImageIcon profilePic, String ip, int port) {
        gui = new GUI(this);
        user = new User(username, profilePic);
        User[] contacts = readContactsFromFile();
        if (contacts != null) {
            gui.updateContacts(contacts);
        }
        client.setUser(user);
        client.connect(ip, port);
    }

    private User[] readContactsFromFile() {
        ArrayList<User> contactList = new ArrayList<>();
        try {
            String filename = System.getProperty("user.dir") + "/" + user.getName() + "_contacts.dat";
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            contactList = (ArrayList) ois.readObject();
            User[] contacts = new User[contactList.size()];
            for (int i = 0; i < contacts.length; i++) {
                contacts[i] = contactList.get(i);
            }
            ois.close();
            fis.close();
            return contacts;
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public void send(String text) {
        ArrayList<User> receivers = gui.getSelectedReceivers();
        receivers.add(user);
        Message message = new Message(user, receivers, text, user.getProfilePic());
        client.send(message);
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

    public void closeConnection() {
        client.close();
        saveContacts();
        gui.close();
        gui = null;
    }

    private void saveContacts() {
        ArrayList<User> contactList = new ArrayList<>();
        Object[] contacts = gui.getContacts();
        for (Object o : contacts) {
            contactList.add((User) o);
        }
        try {
            String filename = System.getProperty("user.dir") + "/" + user.getName() + "_contacts.dat";
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(contactList);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
