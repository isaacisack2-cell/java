package mypackage;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class labelDemo3 {
    public static void print(String text){
        JOptionPane.showMessageDialog(null,text);
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Isaac delete file");
        window.setSize(500,450);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);

        JLabel label = new JLabel("<html><kbd>enter file name to delete</kbd></html>");
        label.setBounds(20,20,200,40);

        JTextField FileName = new JTextField();
        FileName.setBounds(20,60,300,30);

        JButton DeleteBtn = new JButton("Delete");
        DeleteBtn.setBounds(330,60,100,30);

        DeleteBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String MyFileName = FileName.getText();
                if(MyFileName.trim().isEmpty()){
                    print("File name not provided");
                    return;
                }
                try{
                    File MyFile = new File(MyFileName);
                    if(!MyFile.exists()){
                        print("File not found");
                        return;
                    }

                    int confirmation = JOptionPane.showConfirmDialog(window,"are you sure want to delete this file",JOptionPane.YES_NO_OPTION);
                    if(confirmation == JOptionPane.YES_OPTION){
                        boolean is_deleted = MyFile.delete();
                        if(is_deleted){
                            print("file "+MyFile.getName()+" is deleted successfully");
                        }else{
                            print("Permission Denied");
                        }
                    }else if(confirmation == JOptionPane.CLOSED_OPTION){
                        System.out.println("Closed option!");
                    }else{
                        print("File deletion cancelled");
                    }
                }catch(Exception err){
                    JOptionPane.showMessageDialog(window,"Something went wrong!");
                    err.printStackTrace();
                }
            }
        });

        window.add(label);
        window.add(FileName);
        window.add(DeleteBtn);
        window.setVisible(true);
    }
}