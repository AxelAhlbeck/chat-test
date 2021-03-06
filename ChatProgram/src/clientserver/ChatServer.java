package clientserver;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ChatServer implements Runnable {
    private MessageManager messageManager;
    private int port;
    private ArrayList<User> users;
    Thread server = new Thread(this);

    public ChatServer(MessageManager messageManager, int port) {
        this.messageManager = messageManager;
        this.port = port;
        users = new ArrayList<>();
        server.start();
    }

    @Override
    public void run() {
        Socket socket;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message newUser = (Message) ois.readObject();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                users.add(newUser.getSender());
                new Connection(ois, oos);
                sendUsers(users);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sendUsers(ArrayList<User> users) {
        User[] onlineUsers = new User[users.size()];
        for (int i = 0; i < users.size(); i++) {
            onlineUsers[i] = users.get(i);
        }
        Message updateOnline = new Message(new User("SERVER"), onlineUsers, "updateOnline", null);
        messageManager.put(updateOnline);
    }

    private class Connection {
        private Buffer<Message> messageBuffer;
        private Sender sender;
        private Receiver receiver;

        public Connection(ObjectInputStream ois, ObjectOutputStream oos) {
            messageBuffer = new Buffer<>();
            sender = new Sender(oos, messageBuffer);
            receiver = new Receiver(ois, messageBuffer);
            sender.start();
            receiver.start();
        }
    }

    private class Sender extends Thread implements PropertyChangeListener {
        private ObjectOutputStream oos;
        private Buffer<Message> messageBuffer;

        public Sender(ObjectOutputStream oos, Buffer<Message> messageBuffer) {
            this.oos = oos;
            this.messageBuffer = messageBuffer;
            messageManager.addPropertyChangeListener(this);
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Message message = messageBuffer.get();
                    oos.writeObject(message);
                    oos.flush();
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("New message")) {
                Message message = (Message) evt.getNewValue();
                messageBuffer.put(message);
            }
        }
    }

    private class Receiver extends Thread {
        private ObjectInputStream ois;
        private Buffer<Message> messageBuffer;

        public Receiver(ObjectInputStream ois, Buffer<Message> messageBuffer) {
            this.ois = ois;
            this.messageBuffer = messageBuffer;
        }

        public void run() {
            while (true) {
                try {
                    Message message = (Message) ois.readObject();
                    if (message != null) {
                        messageManager.put(message);
                    } else {
                        System.out.println("NULL");
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        Buffer<Message> buffer = new Buffer<>();
        MessageManager manager = new MessageManager(buffer);
        ChatServer server = new ChatServer(manager, 2341);
        manager.start();
    }
}
