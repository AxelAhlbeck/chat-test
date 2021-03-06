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

    public void addMessageListener(Callback callback) {
        callbacks.add(callback);
    }

    public void connect(String ip, int port) {
        connection = new Connection(ip, port);
    }

    public void send(String text) {
        Message message = new Message(user, null, text, user.getProfilePic());
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
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Message userMessage = new Message(user, null, "", user.getProfilePic());
                oos.writeObject(userMessage);
                oos.flush();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                sender = new Sender(oos);
                receiver = new Receiver(ois);
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

        public Receiver(ObjectInputStream inputStream) {
            ois = inputStream;
        }

        public void run() {
            while (true) {
                try {
                    Message message = (Message) ois.readObject();
                    if (message.getSender().getName().equals(user.getName())) {
                        message.setUsername("You");
                    }
                    if (message.getSender().getName().equals("SERVER")) {
                        handleServerMessage(message);
                    } else {
                        history.add(message);
                        for (Callback callback : callbacks) {
                            Message[] messages = new Message[history.size()];
                            for (int i = 0; i < messages.length; i++) {
                                messages[i] = history.get(i);
                            }
                            callback.updateListView(messages);
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleServerMessage(Message message) {
            switch (message.getText()) {
                case "updateOnline":
                    for (Callback callback : callbacks) {
                        callback.updateListView(message.getRecipients());
                    }
            }
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("Axel","127.0.0.1", 2341);
    }
}
