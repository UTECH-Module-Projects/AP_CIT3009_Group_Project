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
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


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
    public GUIEntityTable(Object[][] data, String[] headers, boolean isSortable) {
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
    public void initializeComponents(Object[][] data, boolean isSortable) {
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
    public RowFilter<TableModel, Object> createTextFilter(JTextField txt, int index) {
        return RowFilter.regexFilter("(?i)" + txt.getText(), index);
    }

    public RowFilter<TableModel, Object> createComboBoxFilter(JComboBox<String> cmb, int index) {
        return RowFilter.regexFilter(Objects.requireNonNull(cmb.getSelectedItem()).toString(), index);
    }

    public RowFilter<TableModel, Object> createDateFilter(GUIDatePicker start, GUIDatePicker end, int index) {
        List<RowFilter<TableModel, Object>> filters = new ArrayList<>(2);
        RowFilter<TableModel, Object> startRF = RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, start.getMdl().getValue(), index);
        RowFilter<TableModel, Object> endRF = RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, end.getMdl().getValue(), index);
        filters.add(startRF);
        filters.add(endRF);

        return RowFilter.andFilter(filters);
    }

    public void setRowFilters(List<RowFilter<TableModel, Object>> filters) {
        RowFilter<TableModel, Object> andFilter = RowFilter.andFilter(filters);
        rSort.setRowFilter(andFilter);
    }

    public void setRowFilters(RowFilter<TableModel, Object> ...filters) {
        List<RowFilter<TableModel, Object>> rowFilters = new ArrayList<>(Arrays.asList(filters));
        RowFilter<TableModel, Object> andFilter = RowFilter.andFilter(rowFilters);
        rSort.setRowFilter(andFilter);
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

    public int[] getSelectedRows() {
        return tbl.getSelectedRows();
    }

    public void refresh(Object[][] data) {
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
