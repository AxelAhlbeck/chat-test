package gui;

import clientserver.*;
import controller.ClientController;
import controller.ServerController;

public class Main {
    public static void main(String[] args) {
        Buffer<Message> buffer = new Buffer<>();
        MessageManager manager = new MessageManager(buffer);
        ChatServer server = new ChatServer(manager, 2341);
        ServerController serverController = new ServerController(server);
        manager.start();

        int NUM_CLIENTS = 2;

        for (int i = 0; i < NUM_CLIENTS; i++) {
            new LogInWindow(new ClientController());
        }

    }
}
