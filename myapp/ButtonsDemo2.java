package mypackage;
import javax.swing.*;
import java.awt.event.*;

abstract class UX {
    public static void ask(){
        System.out.println("hello");
    }
} 

public class ButtonsDemo2 extends UX{
    public static void main(String[] args){
        //setting a frame
        JFrame frame = new JFrame("Isaac File Manager");
        frame.setSize(300,250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        //Creating a button
        JButton btn = new JButton("Create new file");
        btn.setBounds(10,30,180,40);
        
        btn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String fileName = JOptionPane.showInputDialog("Enter file name you want to create");
                ask();
                JOptionPane.showMessageDialog(frame,"file "+fileName+" created successfully");
            }
        });

        //finallizing a frame
        frame.add(btn);
        frame.setVisible(true);
    }
}