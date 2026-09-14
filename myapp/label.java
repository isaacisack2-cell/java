package mypackage;
import java.awt.event.*;
import javax.swing.*;

public class label {
    public static void main(String[] main){
        //Settinfg a window frame
        JFrame frame = new JFrame("Isaac demo");
        frame.setSize(400,340);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //creating a label
        JLabel label = new JLabel("<html><h1>Welcome back to <strong>isaac</strong></h1></h3> system <br>building ascii letters and unicode characters using <strong>java</strong></h3></html>");
        label.setSize(label.getPreferredSize());

        //creating sample button
        JButton btn = new JButton("Click me!");
        btn.setBounds(200, 320, 110, 20);

        //adding action listerner to btn
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event){
                JOptionPane.showMessageDialog(frame,"You click the button for whta bro. Thats wahala!");
            }
        });

        //adding contents to frame
        frame.add(label);
        frame.add(btn);
        frame.setVisible(true);
    }
}
