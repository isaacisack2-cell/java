package mypackage;

import java.io.File;
import javax.swing.*;
import java.awt.event.*;

public class ButtonsDemo3 {
    public static void main(String[] args){
        JFrame frame = new JFrame("File Management");
        frame.setSize(600,350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton btn = new JButton("Create new file");
        JButton btn2 = new JButton("Delete a file");

        btn.setBounds(100,100,200,50);
        btn2.setBounds(310,100,200,50);

        btn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String FileName = JOptionPane.showInputDialog("Enter name of file to create");
                if(FileName != null){
                    try{
                        File MyFile = new File(FileName);
                        if(MyFile.createNewFile()){
                            JOptionPane.showMessageDialog(null,"File "+FileName+" Created successfully");
                        }else{
                            JOptionPane.showMessageDialog(null,"File "+FileName+" failed to create");
                        }
                    }catch(Exception err){
                        err.printStackTrace();
                    }
                }
            }
        });
        btn2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e2){
                String FileName = JOptionPane.showInputDialog("Enter correctly name of file to delete");
                if(FileName != null){
                    try {
                        File MyFile = new File(FileName);
                        if(MyFile.exists()){
                            if(true){
                                if(MyFile.delete()){
                                    JOptionPane.showMessageDialog(null, "file "+FileName+" deleted");
                                }else{
                                    JOptionPane.showMessageDialog(null, "error during deleting a file ");
                                }
                            }
                        }else{
                            JOptionPane.showMessageDialog(frame,"File doesnt exist");
                        }
                    } catch (Exception err2) {
                        err2.printStackTrace();
                    }
                }
            }
        });

        frame.add(btn);
        frame.add(btn2);
        frame.setVisible(true);
    }
}