package mypackage;
import java.awt.event.*;
import javax.swing.*;

public class ButtonsDemo {
    public static void main(String[] args){
        JFrame frame = new JFrame("Demo app by isaac isack");
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton myBtn = new JButton("Click here");
        myBtn.setBounds(100,100,150,40);

        myBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Button clicked tap!");
                JOptionPane.showMessageDialog(frame,"Button clicked");
            }
        });

        frame.add(myBtn);
        frame.setVisible(true);
    }
}