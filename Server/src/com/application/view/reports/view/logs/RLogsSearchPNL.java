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
package com.application.view.reports.view.logs;

import com.application.generic.TableList;
import com.application.models.misc.EntityDate;
import com.application.models.tables.Employee;
import com.application.models.tables.Log;
import com.application.view.ServerApp;
import com.application.view.reports.view.RLogsPNL;
import com.application.view.utilities.FileLocationSelector;
import com.application.view.utilities.GUIDatePicker;
import com.application.view.utilities.InvalidCharListener;
import com.application.view.utilities.ReportGenerator;
import com.database.server.Client;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
public class RLogsSearchPNL implements ActionListener, ChangeListener {
    private static final InvalidCharListener charListener = new InvalidCharListener(new char[]{'\\', '(', ')', '*', '.', '?', '|', '+', '$', '^', '/'});

    private final String title;
    private final RLogsPNL rLogsPNL;
    private final Client client;
    private JPanel pnl;
    private JLabel dateStartLBL, dateEndLBL, timeLBL, typeLBL, levelLBL, msgLBL, clientLBL;
    private JTextField timeTXT, msgTXT;
    private GUIDatePicker dateStart, dateEnd;
    private JComboBox<String> typeCMB, levelCMB, clientCMB;
    private JButton print, printAll, refresh, clear;

    /**
     * Primary Constructor
     * @param title
     * @param rLogsPNL
     * @param client
     */
    public RLogsSearchPNL(String title, RLogsPNL rLogsPNL, Client client) {
        this.title = title;
        this.rLogsPNL = rLogsPNL;
        this.client = client;
        initializeComponents();
        addComponents();
        setProperties();
        addTextSearchListeners();
    }

    /**
     * Initializes swing Components used in this search panel
     */
    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10, gapx 10", "[grow 25][grow 25][grow 25][grow 25]", "[][][][]15[]"));

        dateStartLBL = new JLabel("Start:");
        dateEndLBL = new JLabel("End:");
        typeLBL = new JLabel("Type:");
        levelLBL = new JLabel("Level:");
        msgLBL = new JLabel("Message:");
        timeLBL = new JLabel("Time:");
        clientLBL = new JLabel("Client ID:");

        dateStart = new GUIDatePicker();
        dateEnd = new GUIDatePicker();

        typeCMB = new JComboBox<>(ServerApp.types);
        levelCMB = new JComboBox<>(ServerApp.levels);
        msgTXT = new JTextField();
        timeTXT = new JTextField();
        clientCMB = new JComboBox<>();

        print = new JButton("Print", new ImageIcon(ServerApp.printIMG));
        printAll = new JButton("Print All", new ImageIcon(ServerApp.printIMG));
        refresh = new JButton("Refresh", new ImageIcon(ServerApp.refIMG));
        clear = new JButton("Clear", new ImageIcon(ServerApp.clearIMG));
    }

    /**
     * adding components to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(dateStartLBL, "grow");
        pnl.add(dateEndLBL, "grow");
        pnl.add(typeLBL, "grow");
        pnl.add(levelLBL, "grow, wrap");

        pnl.add(dateStart.getDate(), "grow");
        pnl.add(dateEnd.getDate(), "grow");
        pnl.add(typeCMB, "grow");
        pnl.add(levelCMB, "grow, wrap");

        pnl.add(msgLBL, "grow, span 2");
        pnl.add(timeLBL, "grow");
        pnl.add(clientLBL, "grow, wrap");

        pnl.add(msgTXT, "grow, span 2");
        pnl.add(timeTXT, "grow");
        pnl.add(clientCMB, "grow, wrap");

        pnl.add(print, "width 100, span, split 4, left, gapright 10");
        pnl.add(printAll, "width 100, left, gapright 10");
        pnl.add(refresh, "width 100, left, gapright 10");
        pnl.add(clear, "width 100, left");
    }

    /**
     * Sets properties of components
     */
    private void setProperties() {
        pnl.setBorder(BorderFactory.createEtchedBorder());

        typeCMB.insertItemAt("", 0);
        typeCMB.setSelectedIndex(0);

        levelCMB.insertItemAt("", 0);
        levelCMB.setSelectedIndex(0);

        refresh();

        print.addActionListener(this);
        printAll.addActionListener(this);
        refresh.addActionListener(this);
        clear.addActionListener(this);

        timeTXT.addKeyListener(charListener);
        msgTXT.addKeyListener(charListener);

        dateStart.getMdl().addChangeListener(this);
        dateEnd.getMdl().addChangeListener(this);

        typeCMB.addActionListener(this);
        levelCMB.addActionListener(this);
        clientCMB.addActionListener(this);

        rLogsPNL.getLogTBL().createTextFilter(timeTXT, Log.TIME);
        rLogsPNL.getLogTBL().createTextFilter(msgTXT, Log.MESSAGE);
    }

    private void addTextSearchListeners() {
        DocumentListener dl = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setRowFilters();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setRowFilters();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setRowFilters();
            }
        };
        timeTXT.getDocument().addDocumentListener(dl);
        msgTXT.getDocument().addDocumentListener(dl);
    }

    @SuppressWarnings("unchecked")
    public void setRowFilters() {
        rLogsPNL.getLogTBL().setRowFilters(
                rLogsPNL.getLogTBL().createTextFilter(timeTXT, Log.TIME),
                rLogsPNL.getLogTBL().createTextFilter(msgTXT, Log.MESSAGE),
                rLogsPNL.getLogTBL().createComboBoxFilter(typeCMB, Log.TYPE),
                rLogsPNL.getLogTBL().createComboBoxFilter(levelCMB, Log.LEVEL),
                rLogsPNL.getLogTBL().createComboBoxFilter(clientCMB, Log.CLIENT_ID),
                rLogsPNL.getLogTBL().createDateFilter(dateStart, dateEnd, Log.DATE)
        );
    }

    /**
     * Method clears text field
     */
    public void clear() {
        EntityDate today = EntityDate.today();
        timeTXT.setText("");
        msgTXT.setText("");

        dateStart.setDate(today);
        dateEnd.setDate(today);

        typeCMB.setSelectedIndex(0);
        levelCMB.setSelectedIndex(0);
        clientCMB.setSelectedIndex(0);

        print.setEnabled(false);
    }

    public void refresh() {
        clientCMB.setModel(new JComboBox<>(ServerApp.employees.map(Employee::getIdNum).toArray(String[]::new)).getModel());
        clientCMB.insertItemAt("", 0);
        clientCMB.setSelectedIndex(0);

        clear();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(typeCMB) || e.getSource().equals(levelCMB) || e.getSource().equals(clientCMB)) {
            setRowFilters();
        } else if (e.getSource().equals(print) || e.getSource().equals(printAll)) {
            int totRows = rLogsPNL.getLogTBL().getTbl().getRowCount();

            if (e.getSource().equals(printAll) && totRows == 0) {
                JOptionPane.showMessageDialog(rLogsPNL.getPnl(), "No Logs to Print!", title, JOptionPane.ERROR_MESSAGE);
                return;
            }

            int[] rows;
            if (e.getSource().equals(print)) {
                rows = rLogsPNL.getLogTBL().getSelectedRows();
            } else {
                rows = new int[totRows];
                for (int i = 0; i < totRows; i++) {
                    rows[i] = i;
                }
            }

            FileLocationSelector selector = new FileLocationSelector();
            String filePath = selector.getFilePath(rLogsPNL.getPnl(), "Log Report_" + dateStart + "-" + dateEnd + ".pdf");
            if (filePath == null) return;

            TableList<Log, String> logs = new TableList<>(Log.class, Log.headers);
            for (int row : rows) {
                Log log = ServerApp.logs.get(rLogsPNL.getLogTBL().convertRowIndexToModel(row));
                logs.add(log);
            }

            if (ReportGenerator.logReport(filePath, dateStart + " to " + dateEnd, logs)) {
                JOptionPane.showMessageDialog(rLogsPNL.getPnl(), "Log Report successfully generated!", title, JOptionPane.INFORMATION_MESSAGE);

                if (!Desktop.isDesktopSupported()) {
                    JOptionPane.showMessageDialog(rLogsPNL.getPnl(), "Error when opening document!", title, JOptionPane.ERROR_MESSAGE);
                } else selector.openFile(filePath, rLogsPNL.getPnl(), title);
            } else {
                JOptionPane.showMessageDialog(rLogsPNL.getPnl(), "Error when creating document!", title, JOptionPane.ERROR_MESSAGE);
            }
            print.setSelected(false);
        } else if (e.getSource().equals(refresh)) {
            ServerApp.refresh("Log");
            JOptionPane.showMessageDialog(rLogsPNL.getPnl(), "Successfully Refreshed!", title, JOptionPane.INFORMATION_MESSAGE);
            refresh.setSelected(false);
        } else if (e.getSource().equals(clear)) {
            rLogsPNL.clear();
            clear.setSelected(false);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource().equals(dateStart.getMdl()) || e.getSource().equals(dateEnd.getMdl())) {
            setRowFilters();
        }
    }
}
