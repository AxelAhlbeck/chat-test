package gui;

import clientserver.Message;
import controller.ServerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ServerGUI implements ActionListener {
    private JTextField answerTimeFrom;
    private JTextField answerTimeTo;
    private final JLabel instructions = new JLabel("");
    private JList textBox;
    private JButton ok;
    private final ServerController serverController;

    public ServerGUI(ServerController serverController){
        this.serverController = serverController;
        makeWindow();
    }

    //Metod som sätter upp GUI-komponenterna till ett fönster
    public void makeWindow() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Trafic log");

        textBox = new JList();
        JScrollPane scrollPane = new JScrollPane(textBox);
        scrollPane.setBounds(20,60,750,490);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane);


        JLabel timeFrom = new JLabel("Time from YYYY/MM/DD HH:MM");
        timeFrom.setBounds(10,30,300,20);
        panel.add(timeFrom);

        answerTimeFrom = new JTextField(20);
        answerTimeFrom.setBounds(200,30,165,25);
        panel.add(answerTimeFrom);

        JLabel timeTo = new JLabel("Time to YYYY/MM/DD HH:MM");
        timeTo.setBounds(380,30,300,20);
        panel.add(timeTo);

        answerTimeTo = new JTextField(20);
        answerTimeTo.setBounds(555,30,165,25);
        panel.add(answerTimeTo);

        instructions.setBounds(250,5,300,20);
        Font myFont = new Font("Arial", Font.PLAIN, 10);
        instructions.setFont(myFont);
        instructions.setText("Please enter timestamp for the traffic logger!");
        panel.add(instructions);

        ok = new JButton("OK");
        ok.setBounds(725,30,55,25);
        ok.addActionListener(this);
        panel.add(ok);

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ok){
            serverController.saveAndShowTraffic();
        }
    }

    public String getTimeLow() {
        return answerTimeFrom.getText();
    }

    public String getTimeHigh() {
        return answerTimeTo.getText();
    }

    public void updateListView(String[] messages) {
        textBox.setListData(messages);
    }
}
