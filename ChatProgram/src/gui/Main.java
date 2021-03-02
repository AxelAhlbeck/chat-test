package gui;

import clientserver.*;

public class Main {
    public static void main(String[] args) {
        Buffer<Message> buffer = new Buffer<>();
        MessageManager manager = new MessageManager(buffer);
        ChatServer server = new ChatServer(manager, 2341);
        manager.start();

        ChatClient client1 = new ChatClient("127.0.0.1", 2341);
        ChatClient client2 = new ChatClient("127.0.0.1", 2341);
        new GUI(client1);
        new GUI(client2);
    }
}
