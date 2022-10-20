package com.application.view;

import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Testings {

    JFrame frame;
    JTextField fNameTXT, lNameTXT;
    JLabel fNameLBL, lNameLBL;
    JButton submit;

    public Testings() {
        initializeComponents();
        addComponentsToFrame();
        setWindowProperties();
    }

    private void initializeComponents() {
        frame = new JFrame();
        frame.setLayout(new MigLayout());

        fNameLBL = new JLabel("First Name");
        lNameLBL = new JLabel("Last Name");

        fNameTXT = new JTextField();
        lNameTXT = new JTextField();

        submit = new JButton("Submit");
    }

    private void addComponentsToFrame() {
        frame.add(fNameLBL);
        frame.add(fNameTXT, "grow, span 5, wrap");
        frame.add(lNameLBL);
        frame.add(lNameTXT, "grow, span 5, wrap");
        frame.add(submit);
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
