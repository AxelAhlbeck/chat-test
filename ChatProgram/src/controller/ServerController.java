package controller;

import clientserver.ChatServer;
import clientserver.Message;
import gui.ServerGUI;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class ServerController {
    private ChatServer server;
    private ServerGUI gui;
    public ServerController(ChatServer server) {
        gui = new ServerGUI(this);
        this.server = server;
        server.start();
    }

    public void saveAndShowTraffic() {
        LinkedList<Message> history = server.getHistory();
        try {
            String filename = System.getProperty("user.dir") + "/traffic_log.dat";
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(history);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String timeLow = gui.getTimeLow();
        String timeHigh = gui.getTimeHigh();
        ArrayList<Message> list = new ArrayList<>();
        for (Message m : history) {
            if (m.getServerTimestamp().compareTo(timeLow) >= 0 && m.getServerTimestamp().compareTo(timeHigh) <= 0) {
                list.add(m);
            }
        }
        String[] messages = new String[list.size()];
        for (int i = 0; i < messages.length; i++) {
            messages[i] = list.get(i).getLogInfo();
        }
        gui.updateListView(messages);
    }
}
