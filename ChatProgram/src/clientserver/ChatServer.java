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
                Message userMessage = (Message) ois.readObject();
                User newUser = userMessage.getSender();
                addUser(newUser);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                new Connection(newUser, ois, oos);
                sendUsers();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private synchronized void addUser(User newUser) {
        users.add(newUser);
    }

    private void sendUsers() {
        Message updateOnline = new Message(new User("SERVER"), getUsers(), "updateOnline", null);
        messageManager.put(updateOnline);
    }

    private synchronized ArrayList<User> getUsers() {
        return (ArrayList<User>) users.clone();
    }

    private class Connection {
        private Buffer<Message> messageBuffer;
        private Sender sender;
        private Receiver receiver;

        public Connection(User connectedUser, ObjectInputStream ois, ObjectOutputStream oos) {
            messageBuffer = new Buffer<>();
            sender = new Sender(connectedUser, oos, messageBuffer);
            receiver = new Receiver(connectedUser, ois);
            sender.start();
            receiver.start();
        }
    }

    private class Sender extends Thread implements PropertyChangeListener {
        private ObjectOutputStream oos;
        private Buffer<Message> messageBuffer;
        private User connectedUser;

        public Sender(User connectedUser, ObjectOutputStream oos, Buffer<Message> messageBuffer) {
            this.connectedUser = connectedUser;
            this.oos = oos;
            this.messageBuffer = messageBuffer;
            messageManager.addPropertyChangeListener(this);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Message message = messageBuffer.get();
                    for (User u : message.getRecipients()) {
                        if (u.getName().equals(connectedUser.getName())) {
                            oos.writeObject(message);
                            oos.flush();
                            break;
                        }
                    }
                }
                catch (InterruptedException | IOException e) {
                    break;
                }
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
        private User connectedUser;

        public Receiver(User connectedUser, ObjectInputStream ois) {
            this.connectedUser = connectedUser;
            this.ois = ois;
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
                    break;
                }
            }
            removeUser(connectedUser);
            System.out.println("ABABA");
            sendUsers();
        }
    }

    private synchronized void removeUser(User connectedUser) {
        users.remove(connectedUser);
    }


    public static void main(String[] args) {
        Buffer<Message> buffer = new Buffer<>();
        MessageManager manager = new MessageManager(buffer);
        ChatServer server = new ChatServer(manager, 2341);
        manager.start();
    }
}
