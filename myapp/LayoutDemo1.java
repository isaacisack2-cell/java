package mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LayoutDemo1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("ISAAC LAYOUT DEMO1");
        frame.setSize(450,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(5,5));

        JLabel TitleLabel = new JLabel("ISAAC LAYOUT MANAGER LESSON DEMO1",SwingConstants.CENTER);
        frame.add(TitleLabel,BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5,2,30,10));
        String[] buttons = {
            "create file","write file","read file",
            "update file","search file","display files",
            "delete file","recent files","exit"
        };
        for(String btn : buttons){
            JButton BTN = new JButton(btn);
            buttonsPanel.add(BTN);
        }
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30,50,5,50));
        frame.add(buttonsPanel,BorderLayout.CENTER);

        JPanel LeftPanel = new JPanel();
        LeftPanel.setLayout(new GridLayout(2,1,0,5));

        JPanel LeftTitle = new JPanel();
        LeftTitle.setLayout(new FlowLayout());
        LeftTitle.setBackground(Color.LIGHT_GRAY);
        
        JLabel LeftLabel = new JLabel("MENU BARS",SwingConstants.CENTER);
        LeftTitle.add(LeftLabel);
        LeftPanel.add(LeftTitle);

        JPanel LeftMenu = new JPanel();
        String[] menu = {"home","about","services","login"};
        LeftMenu.setLayout(new GridLayout(4,1,1,25));
        for(String menuItem : menu){
            JButton BTN = new JButton(menuItem);
            LeftMenu.add(BTN);
        }
        LeftMenu.setBackground(Color.LIGHT_GRAY);
        LeftPanel.add(LeftMenu);
        LeftPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        LeftPanel.setBackground(Color.LIGHT_GRAY);
        frame.add(LeftPanel,BorderLayout.WEST);
        
        JLabel statusLabel = new JLabel("created and desiged by isaacisack",SwingConstants.CENTER);
        statusLabel.setFont(new Font("monospace",Font.BOLD, 16));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10,5,10,5));
        frame.add(statusLabel,BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}