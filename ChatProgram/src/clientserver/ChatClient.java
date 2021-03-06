package clientserver;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class ChatClient {
    private User user;
    private ArrayList<Message> history;
    private Buffer<Message> messageBuffer;
    private ArrayList<Callback> callbacks;
    private Connection connection;

    public ChatClient(String user, String ip, int port) {
        this.user = new User(user);
        history = new ArrayList<>();
        messageBuffer = new Buffer<>();
        callbacks = new ArrayList<>();
    }

    public ChatClient() {
        this("","",-1);
    }

    public void put(String text) {
        Message message = new Message(user, null, text, null);
        messageBuffer.put(message);
    }

    public void addMessageListener(Callback callback) {
        callbacks.add(callback);
    }

    public void connect(String ip, int port) {
        connection = new Connection(ip, port);
    }

    public void send(String text) {
        Message message = new Message(user, null, text, null);
        messageBuffer.put(message);
    }


    public void setUser(User user) {
        this.user = user;
    }


    private class Connection {
        private Sender sender;
        private Receiver receiver;

        public Connection(String ip, int port) {
            try {
                Socket socket = new Socket(ip, port);
                System.out.println("Connected");
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Message userMessage = new Message(user, null, "", null);
                System.out.println("Sending user");
                oos.writeObject(userMessage);
                oos.flush();
                sender = new Sender(oos);
                receiver = new Receiver(socket.getInputStream());
                sender.start();
                receiver.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class Sender extends Thread {
        private ObjectOutputStream oos;

        public Sender(ObjectOutputStream outputStream) {
            oos = outputStream;
        }

        public void run() {
            while (true) {
                try {
                    Message message = messageBuffer.get();
                    oos.writeObject(message);
                    oos.flush();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private class Receiver extends Thread {
        private ObjectInputStream ois;

        public Receiver(InputStream inputStream) {
            try {
                ois = new ObjectInputStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            while (true) {
                try {
                    Message message = (Message) ois.readObject();
                    if (message.getSender().getName().equals(user.getName())) {
                        message.setUsername("You");
                    }
                    history.add(message);
                    for (Callback callback : callbacks) {
                        String[] infoStrings = new String[history.size()];
                        for (int i = 0; i < infoStrings.length; i++) {
                            infoStrings[i] = history.get(i).toString();
                        }
                        callback.updateListView(infoStrings);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("Axel","127.0.0.1", 2341);
    }
}
