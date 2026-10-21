package testing;

import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.table.TableRowSorter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;


public class TableSearch extends JFrame{
    private static DefaultTableModel tableModel;
    private static TableRowSorter<DefaultTableModel> sorter;
    private static JTable myTable;
    /*private static JTextField nameField;
    private static JTextField courseField;
    private static JTextField gpaField;*/
    private static JTextField searchField;

    public TableSearch(){
        setTitle("Isaac manual live search");
        setSize(600,650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20,20));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        topPanel.add(new JLabel("ISAAC SEARCHING ENGINE",SwingConstants.CENTER));
        topPanel.add(new JLabel("Search: "));
        searchField = new JTextField(30);
        searchField.setBackground(new Color(255, 0, 0));
        topPanel.add(searchField);
        add(topPanel,BorderLayout.NORTH);

        String[] cols = {"name","course","GPA"};
        Object[][] data = {
            {"isaac isack","IT",4.9},
            {"john doe","CS",3.8},
            {"janet smith","IT",3.7}
        };
        tableModel = new DefaultTableModel(data,cols);
        myTable = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        myTable.setRowSorter(sorter);
        add(new JScrollPane(myTable));

        searchField.getDocument().addDocumentListener(new DocumentListener() {
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
    }

    public static void filterTable(){
        String text = searchField.getText().trim();
        if(text.length() == 0){
            sorter.setRowFilter(null);
        }else{
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TableSearch().setVisible(true);
        });
    }
}
