/*
 * Advance Programming Group Project
 * Date of Submission: 11/11/2022
 * Lab Supervisor: Christopher Panther
 *
 * Group Members:-
 * ~ Gabrielle Johnson      2005322
 * ~ Jazmin Hayles          2006754
 * ~ Rushawn White          2002469
 * ~ Barrignton Patternson  2008034
 *
 */
package com.application.view.utilities;

import lombok.Getter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.FocusListener;


@Getter
public class GUIEntityTable {
    /**
     *  Table- tb;
     *  Scroll Pane - sPNE
     *  Default table model - tModel
     *  Table Model Array Sort - rSort
     *  Column - headers
     */
    private JTable tbl;
    private JScrollPane sPNE;
    private DefaultTableModel tModel;
    private TableRowSorter<TableModel> rSort;
    private final String[] headers;

    /**
     *
     * @param data the row
     * @param headers the column
     * @param isSortable sorts the
     */
    public GUIEntityTable(String[][] data, String[] headers, boolean isSortable) {
        this.headers = headers;
        initializeComponents(data, isSortable);
        setProperties();
    }

    /**
     *
     * @param headers
     * @param isSortable
     */
    public GUIEntityTable(String[] headers, boolean isSortable) {
        this.headers = headers;
        initializeComponents(new String[0][0], isSortable);
        setProperties();
    }

    /**
     *
     * @param data
     * @param isSortable
     */
    public void initializeComponents(String[][] data, boolean isSortable) {
        tModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tModel.setDataVector(data, headers);
        tbl = new JTable(tModel);
        if (isSortable) {
            rSort = new TableRowSorter<>(tModel);
            tbl.setRowSorter(rSort);
        }
        sPNE = new JScrollPane(tbl);
    }

    /**
     *
     */
    public void setProperties() {
        tbl.getTableHeader().setReorderingAllowed(false);
    }

    /**
     *
     * @param txt
     * @param index
     */
    public void setTextFieldRowFilter(JTextField txt, int index) {
        txt.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = txt.getText();

                if (text.trim().length() == 0) {
                    rSort.setRowFilter(null);
                } else {
                    // regex ?i will do case Insensitive matches , opposite is ?-i
                    rSort.setRowFilter(RowFilter.regexFilter("(?i)" + text, index));
                }
            }

            /**
             * Removes text
             * @param e the document event
             */
            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = txt.getText();

                if (text.trim().length() == 0) {
                    rSort.setRowFilter(null);
                } else {
                    rSort.setRowFilter(RowFilter.regexFilter("(?i)" + text, index));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    public Object getValueAt(int row, int col) {
        return tModel.getValueAt(row, col);
    }

    public void addRow(String[] data) {
        tModel.addRow(data);
    }

    public void updateRow(int row, String[] data) {
        if (data.length > tbl.getColumnCount()) throw new ArrayIndexOutOfBoundsException("Table overflow!");

        for (int i = 0; i < data.length; i++)
            tbl.setValueAt(data[i], row, i);
    }

    public void refresh(String[][] data) {
        tModel.setDataVector(data, headers);
    }

    public void clear() {
        tModel.setDataVector(new String[0][0], headers);
    }

    public void clearSelection() {
        tbl.clearSelection();
    }

    public ListSelectionModel getSelectionModel() {
        return tbl.getSelectionModel();
    }

    public void addListSelectionListener(ListSelectionListener x) {
        getSelectionModel().addListSelectionListener(x);
    }

    public void addFocusListener(FocusListener x) {
        tbl.addFocusListener(x);
    }

    public int getSelectedRow() {
        return tbl.getSelectedRow();
    }

    public int convertRowIndexToModel(int row) throws IndexOutOfBoundsException {
        return rSort.convertRowIndexToModel(row);
    }
}
