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
                System.out.println("Waiting for connection...");
                socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                System.out.println("Connected, waiting for user info");
                Message newUser = (Message) ois.readObject();
                users.add(newUser.getSender());
                new Connection(socket);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class Connection {
        private Buffer<Message> messageBuffer;
        private Sender sender;
        private Receiver receiver;

        public Connection(Socket socket) {
            try {
                messageBuffer = new Buffer<>();
                sender = new Sender(socket.getOutputStream(), messageBuffer);
                receiver = new Receiver(socket.getInputStream(), messageBuffer);
                sender.start();
                receiver.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class Sender extends Thread implements PropertyChangeListener {
        private ObjectOutputStream oos;
        private Buffer<Message> messageBuffer;

        public Sender(OutputStream outputStream, Buffer<Message> messageBuffer) {
            try {
                this.oos = new ObjectOutputStream(outputStream);
                this.messageBuffer = messageBuffer;
                messageManager.addPropertyChangeListener(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

        public Receiver(InputStream inputStream, Buffer<Message> messageBuffer) {
            try {
                ois = new ObjectInputStream(inputStream);
                this.messageBuffer = messageBuffer;
            } catch (IOException e) {
                e.printStackTrace();
            }
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
