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
import javax.swing.event.*;
//import com.formdev.flatlaf.FlatDarkLaf;

public class Checkout extends JFrame implements ListSelectionListener, ActionListener {
  private JPanel cartPanel;
  private JPanel productPanel;
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
  private JTabbedPane tabsPane;

  // variables to get selected value
  private int[] sel;
  private Object value;
  private Object val2;
  private String val3;
  private String val4;
  // Variables to store chart info
  private Product prodtemp;
  private Table table2;
  private Table table3;
  List<Product> dataProducts = new ArrayList<Product>();

  public void initializeComponents() {
    // product Initialization
    prodtemp = new Product();
    table2= new Table(modeldaList());
    table3= new Table(modeldaList());
    cartPanel=new JPanel();
    cartPanel.setLayout(new MigLayout("insets 0, novisualpadding", "[fill][::100][::500][]", ""));

    // border settings
    // panelBorder = new LineBorder(Color.BLACK, 2, true);

    //tabs settings
    tabsPane= new JTabbedPane();
    

    // Panel Settings
    productPanel = new JPanel();
    productPanel.setBounds(40, 80, 900, 500);
    productPanel.setLayout(new MigLayout("insets 0, novisualpadding", "[fill][::100][::500][]", ""));
    productPanel.setVisible(true);
    productPanel.setBorder(panelBorder);

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

      //LIStener
      ListSelectionModel listModel = table2.getTable().getSelectionModel();
      listModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      listModel.addListSelectionListener(this);
  }

  public void addComponentsToPanel() {
    // Add components to Panel
    productPanel.add(table2.initializeComponents(), "wrap, span, al center");
    productPanel.add(lblTitle, "grow,span,push,wrap,");
    productPanel.add(lblProductName, "width 100,gapright 5");
    productPanel.add(txtProductNameDisplay, "pushx, growx, width 100");
    productPanel.add(lblCost, "width 100");
    productPanel.add(txtCostDisplay, "pushx, growx, width 100,wrap");
    productPanel.add(lblLongDescription, "wrap,gapright 5");
    productPanel.add(txtLongDescription, "wrap, span,growx,gapright 5,gapleft 5");
    productPanel.add(b1, " al left ,gapbottom 5, gapleft 5");
    productPanel.add(b2, " al right ,gapbottom 5");

    //Panels 
    cartPanel.add(table3.initializeComponents());

    //tabs
    tabsPane.add("Products List", productPanel);
    tabsPane.add("Cart", cartPanel);
  }
 public List<Product> modeldaList(){
  List<Product> data = new ArrayList<Product>();

  data.add(new Product("Apple", "200.00", "Lorem ipsum dolor sit amet"));
  data.add(new Product("Apricot", "200.00", "Lorem ipsum dolor sit amet"));
  data.add(new Product("Avocado", "200.00", "Lorem ipsum dolor sit amet"));
  data.add(new Product("Banana", "200.00", "Lorem ipsum dolor sit amet"));
  data.add(new Product("Blackberries", "200.00", "Lorem ipsum dolor sit amet"));
  data.add(new Product("Blueberries", "200.00", "Lorem ipsum dolor sit amet"));
  data.add(new Product("Blackcurrant", "200.00", "Lorem ipsum dolor sit amet"));
  data.add(new Product("Boysenberries", "200.00", "Lorem ipsum dolor sit amet"));
  data.add(new Product("Cherry", "200.00", "Lorem ipsum dolor sit amet"));
  return data;
 }
 

  public void addPanelsToWindow() {
    // this.add(table2.initializeComponents(), "wrap, al center");
    this.add(tabsPane);
  }

 
  public void setWindowProperties() {
    this.setTitle("Jan’s Wholesale and Retail");
    this.setSize(500, 500);
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  // public List<Product> getCart(List<Product> dataProduct){
  //   return dataProduct;
  // }

  public Checkout() {

    this.setLayout(new MigLayout(" insets 20", "[][::100]"));
    initializeComponents();
    addComponentsToPanel();
    addPanelsToWindow();
    setWindowProperties();
    registerListener();
  }

  public void valueChanged(ListSelectionEvent e) {

    if (!e.getValueIsAdjusting()) {
      sel = table2.getTable().getSelectedRows();
      // testcol=table.getColumnName();
      if (sel.length > 0) {
        for (int i = 0; i < 3; i++) {
          // get data from JTable
          TableModel tm = table2.getTable().getModel();
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
    // Look and Feel
//    try {
//      UIManager.setLookAndFeel(new FlatDarkLaf());
//    } catch (Exception ex) {
//      System.err.println("Failed to initialize LaF");
//    }
    new Checkout();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    dataProducts.add(new Product(prodtemp));
    if (e.getSource() == b1) {
      System.out.println("Clicked size=" + dataProducts.size() + "\nData" + dataProducts);
    //  ( (DefaultTableModel) (table3.getTable().getModel())).addRow(new String []{prodtemp.getProductName(),prodtemp.getCost(),prodtemp.getShortDes()});
    table3.tableCreation(dataProducts);
    } else if (e.getSource() == b2) {
      if (dataProducts.size() == 0) {
        JOptionPane.showMessageDialog(this, "The Shoping Cart is Empty Please add Items to cart", "Notice",
            JOptionPane.INFORMATION_MESSAGE);
      } else {
        // tabsPane.setSelectedIndex(1);
        new Chart(dataProducts);
      }
    }
  }
}
