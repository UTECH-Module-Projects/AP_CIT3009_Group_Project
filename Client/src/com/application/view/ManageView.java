package com.application.view;

import com.database.client.Client;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;


public class ManageView {
    private JFrame frame;
    private JTable table;
    private JTextArea Area;
    private JScrollPane scroll1, scroll2;
    private JButton select, clear;
    private JPanel pan1, pan2, pan3;

    String [] column ={"Invoice ID" ,"Bill Date", "Customer ID", "Employee ID"};
    String [][] data = {{"20", "12/12/22", "83745", "77383"},{"47", "1/09/20", "84485", "94747"}};

    public ManageView() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        initializeComponents();
        addComponentsToFrame();
        setWindowProperties();
    }

    public void  initializeComponents(){
        frame = new JFrame("Jan's Wholesale and Retail");
        frame.setLayout(new MigLayout(" debug, fillx", "[][]","[][][]"));


        //table
        table = new JTable(data, column);
        scroll1 = new JScrollPane(table);
        table.setShowGrid(false);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && table.getSelectedRow()!=-1) {

                    String id = table.getValueAt(table.getSelectedRow(), 0).toString();
                    String billdate = table.getValueAt(table.getSelectedRow(), 1).toString();
                    String empID = table.getValueAt(table.getSelectedRow(), 2).toString();
                    String custId = table.getValueAt(table.getSelectedRow(), 3).toString();

                    Area.setText(Area.getText() + "\n\n");
                    Area.setText(Area.getText() + id +"\t"+billdate+"\t"+empID +"\t" + custId);

                }
            }

        });



        //Area
        Area = new JTextArea(30, 30);
        scroll2 = new JScrollPane(Area);
        Area.setEditable(false);

        //button
        select = new JButton("Select");
        clear = new JButton("Clear");

        //panels
        pan1 = new JPanel();
        pan2 = new JPanel();

        pan1.add(scroll1);
        pan1.setBorder(new EmptyBorder(5,5,5,5));
        pan1.setBounds(0,0,250, 300);
        pan2.add(scroll2);

    }
    public void addComponentsToFrame(){

        frame.add(pan1," span 3, grow, split");
        frame.add(pan2," gaptop 10, wrap, span 3");
        frame.add(select, "split");
        frame.add(clear);

    }
    public void setWindowProperties(){
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 500);
    }
    public static void main (String[] args){
        new ManageView();
    }
}
