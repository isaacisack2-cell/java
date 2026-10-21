package isaac;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Scanner;
import javax.swing.table.TableRowSorter;
import javax.swing.text.Document;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentListener;

public class ContactBook {
    public static final String FILE_NAME = "contacts.txt";
    public static File Contacts;
    public static String text;
    private static JFrame frame;
    private static javax.swing.table.DefaultTableModel tableModel;
    private static JLabel titleLabel;
    private static JTable contactTable;
    private static JTextField nameField;
    private static JTextField numberField;
    private static JTextField emailField;
    private static JTextField searchField;
    private static TableRowSorter<DefaultTableModel> sorter;
    private static JButton addBtn;
    private static JButton deleteBtn;

    private static void msg_bx(String text){
        JOptionPane.showMessageDialog(frame,text);
    }

    private static void clear(){
        nameField.setText("");
        numberField.setText("");
        emailField.setText("");
    }

    private static void reload_table(){
        try{
            tableModel.setRowCount(0);
            try(Scanner kbd = new Scanner(new File(FILE_NAME))){
                while(kbd.hasNextLine()){
                    text = kbd.nextLine();
                    if(text.trim().isEmpty()) continue;
                    String[] parts = text.split("\\|");
                    tableModel.addRow(new Object[]{parts[0],parts[1],parts[2]});
                    //System.out.printf("%-10s | %-15s | %-20s %n",parts[0],parts[1],parts[2]);
                }
            }catch(Exception err){
                msg_bx("Error during loading contacts");
                err.printStackTrace();
            }
        }catch(Exception ioerr){
            msg_bx("Something went wrong");
            ioerr.printStackTrace();
        }
    }
    
    private static void sync(){
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for(int i = 0 ; i < tableModel.getRowCount() ; i++){
                writer.write(tableModel.getValueAt(i, 0)+"|"+tableModel.getValueAt(i, 1)+"|"+tableModel.getValueAt(i, 2)+System.lineSeparator());
            }
        } catch (Exception e) {
            msg_bx("Unknown error occured");
            e.printStackTrace();
        }
    }

    private static void add_contact(){
        try{
                String name = nameField.getText().trim();
                String number = numberField.getText().trim();
                String email = emailField.getText().trim();

                if(name.isEmpty() || number.isEmpty() || email.isEmpty()){
                    msg_bx("Fill out all the fields");
                    return;
                }
                try(FileWriter writer = new FileWriter(FILE_NAME,true)){
                    writer.write(name+"|"+number+"|"+email+System.lineSeparator());
                    tableModel.addRow(new Object[]{name,number,email});
                    clear();
                    msg_bx("new contact "+name+" saved successfully");
                }catch(Exception err){
                    msg_bx("Unknown error during writing into file "+FILE_NAME);
                    err.printStackTrace();
                }finally{
                    reload_table();
                }
        }catch(Exception ioerr){
            msg_bx("Something went wrong");
            ioerr.printStackTrace();
        }
    }

    private static void delete_contact(){
        int selectedRow = (int)contactTable.getSelectedRow();
        if(selectedRow == -1){
            msg_bx("Select atleast one row first");
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(frame,"Are you sure want to delete this contact?","The process can't be reversed",JOptionPane.YES_NO_OPTION);
        if(confirmation == JOptionPane.YES_OPTION){
            tableModel.removeRow(selectedRow);
            msg_bx("Contact removed");
            sync();
        }else if(confirmation == JOptionPane.NO_OPTION){
            msg_bx("Contact not removed. The process cancelled");
        }else{
            msg_bx("Cancelled");
      }
    }

    private static void preview(){
        contactTable.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){
                int selectedRow = contactTable.getSelectedRow();
                if(selectedRow != -1){
                    nameField.setText(tableModel.getValueAt(selectedRow,0).toString());
                    numberField.setText(tableModel.getValueAt(selectedRow,1).toString());
                    emailField.setText(tableModel.getValueAt(selectedRow,2).toString());
                }
            }
        });
    }

    private static void filterTable(){
        String searchString = searchField.getText().trim();
        if(searchString.length() == 0){
            sorter.setRowFilter(null);
        }else{
            sorter.setRowFilter(RowFilter.regexFilter("(?i)"+searchString));
        }
    }
    public static void main(String[] args){
        frame = new JFrame("Isaac Contact Book");
        frame.setSize(500,450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(20,20));
        //reload_table();

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(2,1));
        titleLabel = new JLabel("<html><h1>ISAAC CONTACT BOOK</h1></html>",SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial",Font.BOLD,25));
        titleLabel.setForeground(new Color(0,0,15));
        titlePanel.add(titleLabel);
        
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,15,10));
        searchField = new JTextField(10);
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        titlePanel.add(searchPanel);
        frame.add(titlePanel,BorderLayout.NORTH);

        String[] cols = {"name","number","email"};
        tableModel = new DefaultTableModel(cols,0){
            @Override
            public boolean isCellEditable(int row,int column){ return column != 1;}
        };
        reload_table();
        contactTable = new JTable(tableModel);
        preview();
        frame.add(new JScrollPane(contactTable),BorderLayout.CENTER);

        sorter = new TableRowSorter<>(tableModel);
        contactTable.setRowSorter(sorter);
        searchField.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e){
                filterTable();
            }
            @Override
            public void removeUpdate(DocumentEvent e){
                filterTable();
            }
            @Override
            public void changedUpdate(DocumentEvent e){
                filterTable();
            }
        });

        JPanel controllPanel = new JPanel();
        controllPanel.setLayout(new FlowLayout(FlowLayout.CENTER,15,10));
        addBtn = new JButton("ADD");
        deleteBtn = new JButton("DELETE");
        nameField = new JTextField(10);
        numberField = new JTextField(10);
        emailField = new JTextField(10);
        controllPanel.add(new JLabel("NAME:"));
        controllPanel.add(nameField);
        controllPanel.add(new JLabel("NUMBER:"));
        controllPanel.add(numberField);
        controllPanel.add(new JLabel("EMAIL:"));
        controllPanel.add(emailField);
        controllPanel.add(addBtn);
        controllPanel.add(deleteBtn);
        controllPanel.setBorder(BorderFactory.createEmptyBorder(20,10,15,10));
        frame.add(controllPanel,BorderLayout.SOUTH);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event){
                add_contact();
            }
        });

        deleteBtn.addActionListener(e -> {
            delete_contact();
        });

        frame.setVisible(true);
    }
}