package com.application.view;

import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

import javax.swing.table.*;

import net.miginfocom.swing.MigLayout;

import javax.swing.event.*;

public class Chart extends JFrame implements ListSelectionListener {
    private JTable table;
    private JPanel panel;
    private JLabel lblCost;
    private JLabel lblTitle;
    private JLabel lblProductName;
    private JLabel lblLongDescription;
    private JLabel lblCostDisplay;
    private JLabel lblProductNameDisplay;
    private JTextArea txtLongDescription;
    private Border panelBorder;
    private JButton b1;
    private JScrollPane scroll;

    public void initializeComponents() {
        // border settings
        panelBorder = new LineBorder(Color.BLACK, 2, true);

        // Panel Settings
        panel = new JPanel();
        panel.setBounds(40, 80, 500, 200);
        panel.setLayout(new MigLayout());
        panel.setVisible(true);
        panel.setBorder(panelBorder);

        // Button creation
        b1 = new JButton("Add to Cart");
        b1.setBounds(50, 100, 80, 30);
        b1.setBackground(Color.gray);

        // Label creation
        lblTitle = new JLabel("Jan’s Wholesale and Retail Chart");
        lblCost = new JLabel("Product Cost:");
        lblProductName = new JLabel("Product Name: ");
        lblLongDescription = new JLabel("Long Description");

        // Display Creation
        lblCostDisplay = new JLabel();
        lblProductNameDisplay = new JLabel();
        txtLongDescription = new JTextArea(10, 10);
        lblProductName.setBorder(panelBorder);
        lblProductNameDisplay.setBorder(panelBorder);

        // title set up
        lblTitle.setBackground(Color.BLUE);
        lblTitle.setOpaque(true);
        lblTitle.setForeground(Color.WHITE);

        // Add Scroll to table
        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(500, 300));

    }

    public void addComponentsToPanel() {
        // Add components to Panel
        panel.add(lblTitle, "grow,span,wrap");
        panel.add(lblProductName);
        panel.add(lblProductNameDisplay);
        panel.add(lblCost);
        panel.add(lblCostDisplay, "wrap");
        panel.add(lblLongDescription, "wrap");
        panel.add(txtLongDescription, "wrap");
        panel.add(b1);
    }

    public void tableCreation(List<Product> data) {
        // JTable Headers
        String[] columns = new String[] {
                "Product Name",
                "Cost",
                "Short Description"
        };

        // data for JTable in a 2D table
        // Object[][] data = new Object[][] {
        //         { "Apple", 200.0, "Lorem ipsum dolor sit amet" },
        //         { "Apricot", 200.0, "Lorem ipsum dolor sit amet" },
        //         { "Avocado", 200.0, "Lorem ipsum dolor sit amet" },
        //         { "Banana", 200.0, "Lorem ipsum dolor sit amet" },
        //         { "Blackberries", 200.0, "Lorem ipsum dolor sit amet" },
        //         { "Blackcurrant", 200.0, "Lorem ipsum dolor sit amet" },
        //         { "Blueberries", 200.0, "Lorem ipsum dolor sit amet" },
        //         { "Boysenberries", 200.0, "Lorem ipsum dolor sit amet" },
        //         { "Capers", 200.0, "Lorem ipsum dolor sit amet" },
        //         { "Cherry", 200.0, "Lorem ipsum dolor sit amet" },
        // };

        // set the TableModel to get data from JTable
        TableModel model = new AbstractTableModel() {
            private static final long serialVersionUID = 1L;

            public int getColumnCount() {
                return columns.length;
            }

            public int getRowCount() {
                return data.size();
            }

            public Object getValueAt(int row, int col) {
                if(col==0){
                    return data.get(row).getProductName();
                  }
                  if(col==1){
                    return data.get(row).getCost();
                  } 
                  if(col==2){
                    return data.get(row).getShortDes();
                  }
                  return null;
            }

            public String getColumnName(int column) {
                return columns[column];
            }

            public Class<?> getColumnClass(int col) {
                return getValueAt(0, col).getClass();
            }

            public void setValueAt(Object aValue, int row, int column) {
                // data[row][column] = aValue;
                if(column==0){
                    data.get(row).getProductName();
                   }
                   if(column==1){
                     data.get(row).getCost();
                   } 
                   if(column==2){
                     data.get(row).getShortDes();
                   }
                   fireTableCellUpdated(row, column);
            }
        };

        table = new JTable(model);

        ListSelectionModel listModel = table.getSelectionModel();
        listModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModel.addListSelectionListener(this);
    }

    public void addPanelsToWindow() {
        this.getContentPane().add(scroll, "wrap");
        this.add(panel);
    }

    public void setWindowProperties() {
        this.setSize(500, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public Chart(List<Product> data) {
        this.setLayout(new MigLayout());
        tableCreation(data);
        initializeComponents();
        addComponentsToPanel();
        addPanelsToWindow();
        setWindowProperties();

    }

    public void valueChanged(ListSelectionEvent e) {
        int[] sel;
        // int [] testcol;
        Object value;
        Object val2;
        String val3;
        String val4;

        if (!e.getValueIsAdjusting()) {
            sel = table.getSelectedRows();
            // testcol=table.getColumnName();
            if (sel.length > 0) {
                for (int i = 0; i < 3; i++) {
                    // get data from JTable
                    TableModel tm = table.getModel();
                    value = tm.getValueAt(sel[0], i);
                    val2 = tm.getColumnName(i);
                    val3 = val2.toString();
                    val4 = value.toString();
                    if (val3.equalsIgnoreCase("Product Name")) {
                        lblProductNameDisplay.setText(val4);
                    } else if (val3.equalsIgnoreCase("Cost")) {
                        lblCostDisplay.setText(val4);
                    } else if (val3.equalsIgnoreCase("Short Description")) {
                        txtLongDescription.setText(val4);
                    }
                    System.out.print(value + " ");
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        // new Chart();
    }

}