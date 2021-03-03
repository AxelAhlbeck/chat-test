package clientserver;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatClient {
    private String username;
    private ArrayList<Message> history;
    private Buffer<Message> messageBuffer;
    private ArrayList<Callback> callbacks;

    public ChatClient(String username, String ip, int port) {
        this.username = username;
        history = new ArrayList<>();
        messageBuffer = new Buffer<>();
        callbacks = new ArrayList<>();
        new Connection(ip, port);
    }

    public void put(String text) {
        Message message = new Message(username, text, null);
        messageBuffer.put(message);
    }

    public void addMessageListener(Callback callback) {
        callbacks.add(callback);
    }

    private class Connection {
        private Sender sender;
        private Receiver receiver;

        public Connection(String ip, int port) {
            try {
                Socket socket = new Socket(ip, port);
                sender = new Sender(socket.getOutputStream());
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
        private Scanner scan;

        public Sender(OutputStream outputStream) {
            try {
                oos = new ObjectOutputStream(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                    if (message.getUsername().equals(username)) {
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
