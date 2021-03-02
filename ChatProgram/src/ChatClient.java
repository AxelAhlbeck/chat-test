import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatClient {
    private ArrayList<Message> history;

    public ChatClient(String ip, int port) {
        history = new ArrayList<>();
        new Connection(ip, port);
    }

    private class Connection {
        private String ip;
        private int port;
        private Sender sender;
        private Receiver receiver;

        public Connection(String ip, int port) {
            this.ip = ip;
            this.port = port;
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
                scan = new Scanner(System.in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            while (true) {
                System.out.println("TYPE TO SEND:");
                String input = scan.nextLine();
                Message message = new Message(input, null);
                try {
                    oos.writeObject(message);
                    oos.flush();
                } catch (IOException e) {
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
                    history.add(message);
                    System.out.println(message);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("127.0.0.1", 2341);
    }
}
