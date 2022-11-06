package com.application.view;

import javax.swing.event.ListSelectionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.*;
import net.miginfocom.swing.MigLayout;
// import net.miginfocom.swing.MigLayout;
import javax.swing.event.*;
// import com.application.view.Product;
import com.formdev.flatlaf.FlatLightLaf;

public class Checkout extends JFrame implements ListSelectionListener, ActionListener {
  private JTable table;
  private JPanel panel;
  private JLabel lblCost;
  private JLabel lblTitle;
  private JLabel lblProductName;
  private JLabel lblLongDescription;
  private JTextField txtCostDisplay;
  private JTextField txtProductNameDisplay;
  private JTextArea txtLongDescription;
  private Border panelBorder;
  private JButton b1;
  private JButton b2;
  private JScrollPane scroll;

  // variables to get selected value
  private int[] sel;
  private Object value;
  private Object val2;
  private String val3;
  private String val4;
  // Variables to store chart info
  private Product prodtemp;
  List<Product> datap = new ArrayList<Product>();

  int i =1;
  public void initializeComponents() {
    this.setTitle("Jan’s Wholesale and Retail");
    prodtemp = new Product();
    System.out.println("Product test " + prodtemp);
    // border settings
    panelBorder = new LineBorder(Color.BLACK, 2, true);

    // Panel Settings
    panel = new JPanel();
    panel.setBounds(40, 80, 900, 500);
    panel.setLayout(new MigLayout("insets 0, novisualpadding", "[::100][][::500][]", ""));
    panel.setVisible(true);
    panel.setBorder(panelBorder);

    // Button creation
    b1 = new JButton("Add to Cart");
    b1.setBounds(50, 100, 80, 30);
    b1.setBackground(Color.gray);

    // view chart button
    b2 = new JButton("View Cart");
    b2.setBounds(50, 100, 80, 30);
    b2.setBackground(Color.gray);

    // Label creation
    lblTitle = new JLabel("Jan’s Wholesale and Retail Products Details", JLabel.CENTER);
    lblCost = new JLabel("Product Cost:");
    lblProductName = new JLabel("Product Name: ");
    lblLongDescription = new JLabel("Long Description");

    // Display Creation
    txtCostDisplay = new JTextField();
    txtProductNameDisplay = new JTextField();
    txtLongDescription = new JTextArea(10, 10);

    // Disable Textboxs
    txtCostDisplay.setEnabled(false);
    txtProductNameDisplay.setEnabled(false);
    txtLongDescription.setEnabled(false);

    // set Disabled text color
    txtCostDisplay.setDisabledTextColor(Color.black);
    txtProductNameDisplay.setDisabledTextColor(Color.black);
    txtLongDescription.setDisabledTextColor(Color.black);

    // title set up
    lblTitle.setBackground(Color.BLUE);
    lblTitle.setOpaque(true);
    lblTitle.setForeground(Color.WHITE);

    // add table to scroll
    scroll = new JScrollPane(table);
    scroll.setPreferredSize(new Dimension(500, 300));

  }

  public void addComponentsToPanel() {
    // Add components to Panel
    panel.add(lblTitle, "grow,span,push,wrap,");
    panel.add(lblProductName, "width 100,gapright 5");
    panel.add(txtProductNameDisplay, "pushx, growx, width 100");
    panel.add(lblCost, "width 100");
    panel.add(txtCostDisplay, "pushx, growx, width 100,wrap");
    panel.add(lblLongDescription, "wrap,gapright 5");
    panel.add(txtLongDescription, "wrap, span,growx,gapright 5,gapleft 5");
    panel.add(b1, " al left ,gapbottom 5, gapleft 5");
    panel.add(b2, " al right ,gapbottom 5");
  }

  public void tableCreation() {
    // JTable Headers
    String[] columns = new String[] {
        "Product Name",
        "Cost",
        "Short Description"
    };

    // data for JTable in a 2D table
    // Object[][] data = new Object[][] {
    // { "Apple", 200.0,"Lorem ipsum dolor sit amet"},
    // { "Apricot", 200.0,"Lorem ipsum dolor sit amet"},
    // { "Avocado", 200.0,"Lorem ipsum dolor sit amet"},
    // { "Banana", 200.0,"Lorem ipsum dolor sit amet"},
    // { "Blackberries", 200.0,"Lorem ipsum dolor sit amet"},
    // { "Blackcurrant", 200.0,"Lorem ipsum dolor sit amet"},
    // { "Blueberries", 200.0,"Lorem ipsum dolor sit amet"},
    // { "Boysenberries", 200.0,"Lorem ipsum dolor sit amet"},
    // { "Capers", 200.0,"Lorem ipsum dolor sit amet"},
    // { "Cherry", 200.0,"Lorem ipsum dolor sit amet"},
    // };

    // Create New Data List
    List<Product> data = new ArrayList<Product>();
    // Add Data to list
    data.add(new Product("Apple", "200.00", "Lorem ipsum dolor sit amet"));
    data.add(new Product("Apricot", "200.00", "Lorem ipsum dolor sit amet"));
    data.add(new Product("Avocado", "200.00", "Lorem ipsum dolor sit amet"));
    data.add(new Product("Banana", "200.00", "Lorem ipsum dolor sit amet"));
    data.add(new Product("Blackberries", "200.00", "Lorem ipsum dolor sit amet"));
    data.add(new Product("Blueberries", "200.00", "Lorem ipsum dolor sit amet"));
    data.add(new Product("Blackcurrant", "200.00", "Lorem ipsum dolor sit amet"));
    data.add(new Product("Boysenberries", "200.00", "Lorem ipsum dolor sit amet"));
    data.add(new Product("Cherry", "200.00", "Lorem ipsum dolor sit amet"));

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
        // return data[row][col];
        if (col == 0) {
          return data.get(row).getProductName();
        }
        if (col == 1) {
          return data.get(row).getCost();
        }
        if (col == 2) {
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
        if (column == 0) {
          data.get(row).getProductName();
        }
        if (column == 1) {
          data.get(row).getCost();
        }
        if (column == 2) {
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
    this.getContentPane().add(scroll, "wrap, al center");
    this.add(panel, "growx,al center");
  }

  public void setWindowProperties() {
    this.setSize(500, 500);
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public Checkout() {

    // Look and Feel
    try {
      UIManager.setLookAndFeel(new FlatLightLaf());
    } catch (Exception ex) {
      System.err.println("Failed to initialize LaF");
    }

    this.setLayout(new MigLayout(" insets 20", "[][::100]"));
    tableCreation();
    initializeComponents();
    addComponentsToPanel();
    addPanelsToWindow();
    setWindowProperties();
    registerListener();
  }

  public void valueChanged(ListSelectionEvent e) {

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
            txtProductNameDisplay.setText(val4);
            prodtemp.setProductName(val4);

          } else if (val3.equalsIgnoreCase("Cost")) {
            txtCostDisplay.setText(val4);
            prodtemp.setCost(val4);
          } else if (val3.equalsIgnoreCase("Short Description")) {
            txtLongDescription.setText(val4);
            prodtemp.setShortDes(val4);
          }
          System.out.print(value + " ");
        }
        System.out.println();
      }
    }
  }

  public void registerListener() {
    b1.addActionListener(this);
    b2.addActionListener(this);
  }

  public static void main(String[] args) {
    new Checkout();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // System.out.println("\n\n Initial size "+data.size());
    // Product prodtempAdd=prodtemp;
    datap.add(new Product(prodtemp));

    if (e.getSource() == b1) {
      System.out.println("Clicked size=" + datap.size() + "\nData" + datap);
      // chart.tableCreation(data);
    } else if (e.getSource() == b2) {
      if (datap.size() == 0) {
        JOptionPane.showMessageDialog(this, "The Shoping Cart is Empty Please add Items to cart", "Notice",
            JOptionPane.INFORMATION_MESSAGE);
      } else {
        new Chart(datap);
      }
    }
  }
}
