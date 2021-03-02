package gui;

import javax.swing.*;
import java.awt.*;

public class TextBoxPanel extends JPanel {
    private JTextField textField;
    private JButton sendButton;

    public TextBoxPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.yellow);
        

    }
}
