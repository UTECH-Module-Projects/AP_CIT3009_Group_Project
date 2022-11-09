package com.application.view;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class Testings {

    JFrame frame;
    JTextField fNameTXT, lNameTXT;
    JLabel fNameLBL, lNameLBL;
    JButton submit;

    public Testings() {
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

    private void initializeComponents() {
        frame = new JFrame();
        frame.setLayout(new MigLayout("wrap, insets 20, fillx, debug", "[][]", "[][][]"));

        fNameLBL = new JLabel("First Name");
        lNameLBL = new JLabel("Last Name");

        fNameTXT = new JTextField();
        lNameTXT = new JTextField();

        submit = new JButton("Submit");
    }

    private void addComponentsToFrame() {
        String lblConst = "pushx, growx, width 200";

        frame.add(fNameLBL);
        frame.add(fNameTXT, lblConst);
        frame.add(lNameLBL);
        frame.add(lNameTXT, lblConst);
        frame.add(submit, "span, al center");
    }

    private void setWindowProperties() {
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Testings();
    }
}
