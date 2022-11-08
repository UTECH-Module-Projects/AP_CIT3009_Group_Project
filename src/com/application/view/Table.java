package com.application.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class Table {
  private JTable table;
  private  TableModel model;

  public void tableCreation() {
    // JTable Headers
    String[] columns = new String[] {
     "Product Name",
     "Cost",
     "Short Description"
 };
 
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
     model = new AbstractTableModel() {
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
   }


}
