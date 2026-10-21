package mypackage;

import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import javax.swing.*;

public class labelDemo2 {
    public static void main(String[] args){
        JFrame frame = new JFrame("Label Demo My version");
        frame.setSize(400,450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label1 = new JLabel("Enter file name");
        label1.setBounds(20,30,300,30);

        JTextField FileName = new JTextField();
        FileName.setBounds(20,60,300,30);

        JLabel label2 = new JLabel("Enter file contents");
        label2.setBounds(20,100,300,30);

        JTextArea FileContent = new JTextArea();
        FileContent.setLineWrap(true);
        FileContent.setWrapStyleWord(true);

        JScrollPane ScrollPane = new JScrollPane(FileContent);
        ScrollPane.setBounds(20,130,300,200);

        JButton SaveBtn = new JButton("save");
        SaveBtn.setBounds(200,340,100,35);

        SaveBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String MyFileName = FileName.getText();
                String MyFileContent = FileContent.getText();
                try{
                    File MyFile = new File(MyFileName);
                    if(MyFile.createNewFile()){
                        JOptionPane.showMessageDialog(frame, "File "+MyFileName+" Created successfully");
                        FileWriter writer = new FileWriter(MyFile.getName());
                        writer.write(MyFileContent);
                        System.out.println(MyFile.getName()+MyFileContent);
                        writer.close();
                    }else{
                        JOptionPane.showMessageDialog(frame, "file "+MyFile.getName()+" Already exists");
                    }

                }catch(Exception err){
                    JOptionPane.showMessageDialog(null,"Some thing went wrong");
                    err.printStackTrace();
                }
            }
        });

        frame.add(label1);
        frame.add(label2);
        frame.add(FileName);
        frame.add(ScrollPane);
        frame.add(SaveBtn);
        frame.setVisible(true);
    }
}