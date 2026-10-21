package myapplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ListDemo1 {
    public static void main(String[] args){
        JFrame frame = new JFrame("Isaac list demo");
        frame.setSize(450,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setBorder(BorderFactory.createEmptyBorder(10,50,10,50));
        frame.setLayout(new BorderLayout(10,20));

        JLabel label = new JLabel("<html><h1>LIST MODEL 1</h1></html>",SwingConstants.CENTER);
        frame.add(label,BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Task 1");
        listModel.addElement("Task 2");
        listModel.addElement("Task 3");
        listModel.addElement("Task 4");

        JList myList = new JList(listModel);
        JScrollPane scrollPane = new JScrollPane(myList);
        frame.add(scrollPane,BorderLayout.CENTER);

        JPanel controllPanel = new JPanel();
        controllPanel.setLayout(new FlowLayout(10));
        frame.add(controllPanel,BorderLayout.SOUTH);
        
        JTextField textField = new JTextField(10);
        controllPanel.add(textField);

        JButton addBtn = new JButton("add");
        controllPanel.add(addBtn);

        addBtn.addActionListener(e -> {
            String newTask = textField.getText().trim();
            if(!newTask.isEmpty()){
                listModel.addElement(newTask);
                textField.setText("");
                return;
            }
            JOptionPane.showMessageDialog(frame,"Please enter task name first in the field!");
        });

        JButton removeBtn = new JButton("Remove");
        controllPanel.add(removeBtn);

        removeBtn.addActionListener( e -> {
                int selectedIndex = myList.getSelectedIndex();
                if(selectedIndex != -1){
                    listModel.remove(selectedIndex);
                    return;
                }
                JOptionPane.showMessageDialog(frame,"no item selected");
        });

        JButton updateBtn = new JButton("Edit");
        controllPanel.add(updateBtn);

        updateBtn.addActionListener( e -> {
            int selectedIndex = myList.getSelectedIndex();
            String task = textField.getText().trim();
            if(selectedIndex != -1 && !task.isEmpty()){
                listModel.remove(selectedIndex);
                listModel.addElement(task);
                textField.setText("");
                return;
            }else{
                if(selectedIndex == -1){
                    JOptionPane.showMessageDialog(frame,"please select a task from list");
                }else{
                    JOptionPane.showMessageDialog(frame, "Please fill out the field");
                }
            }
        });


        frame.setVisible(true);
    }
}