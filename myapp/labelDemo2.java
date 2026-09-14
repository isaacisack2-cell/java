package mypackage;

import javax.swing.*;
import java.awt.event.*;

public class labelDemo2 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("ISAAC FILE MANAGER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setLayout(null);

        JLabel label = new JLabel("This is my first java label where the layout is set automattically by java itself. ");
        label.setSize(label.getPreferredSize());
        
    }

}