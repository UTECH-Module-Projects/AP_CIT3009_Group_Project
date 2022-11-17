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
package com.application.view;

import com.application.generic.SQLCond;
import com.application.generic.SQLCondBuilder;
import com.application.generic.SQLType;
import com.application.models.tables.Employee;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class LoginPage {
    private JFrame frame;
    private JLabel id, email, titl, titl4;
    private JTextField emailTxt, idTXT;
    private JButton login;
    private JPanel side, account;
    private int totAttempts = 0;

    public static final int MAX_ATTEMPTS = 3;


    public LoginPage() {
        initializeComponents();
        addComponentsToFrame();
        setProperties();
    }


    public void initializeComponents() {
        // region frame
        frame = new JFrame("Welcome to Jan's Wholesale & Retail");
        frame.setLayout(new MigLayout("ins 0, fill", "[grow 0][]", "[]"));
        //endregion

        //region Icon
        frame.setIconImage(new ImageIcon(ServerApp.logoIMG).getImage());
        //endregion

        //region heading
        titl = new JLabel("Jan's Wholesale & Retail", JLabel.CENTER);
        titl4 = new JLabel("Login to Your Account");
        //endregion

        //region Instantiating
        //labels
        email = new JLabel("Email: ");
        id = new JLabel("ID Number:");

        //buttons
        login = new JButton("Login");

        //email text box
        emailTxt = new JTextField(20);
        emailTxt.setText("Enter Email");

        //Id text box
        idTXT = new JTextField(20);
        idTXT.setText("Enter ID Number");

        //account panel
        account = new JPanel(new MigLayout("fill, ins 10 10 20 10", "[][][][]", "[][]10[grow][grow][]"));

        //region Side Panel
        side = new JPanel();
        side.add(new JLabel(new ImageIcon(ServerApp.logoIMG)), "grow");
        //endregion

        // region text box action
        emailTxt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailTxt.getText().equals("Enter Email")) {
                    emailTxt.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailTxt.getText().equals("")) {
                    emailTxt.setText("Enter Email");
                }
            }
        });
        idTXT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (idTXT.getText().equals("Enter ID Number")) {
                    idTXT.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (idTXT.getText().equals("")) {
                    idTXT.setText("Enter ID Number");
                }

            }
        });

        //endregion

        //region Font
        Font myFont = new Font(id.getFont().getFontName(), Font.BOLD, 24);
        Font myFont2 = new Font(id.getFont().getFontName(), Font.BOLD, 12);

        titl.setFont(myFont);
        titl4.setFont(myFont);
        email.setFont(myFont2);
        id.setFont(myFont2);
        //endregion

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServerApp.logEmp = (Employee) ServerApp.client.findMatch("Employee", new SQLCondBuilder("idNum", SQLCond.EQ, idTXT.getText(), SQLType.TEXT), new SQLCondBuilder("email", SQLCond.EQ, emailTxt.getText(), SQLType.TEXT));
                if (ServerApp.logEmp == null) {
                    JOptionPane.showMessageDialog(frame, "Your Username or password is incorrect! (" + (MAX_ATTEMPTS - totAttempts++) + " Attempts Remaining)", "Login", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (ServerApp.logEmp.getType().equals("Admin") || ServerApp.logEmp.getType().equals("Cashier") || ServerApp.logEmp.getType().equals("Manager")) {
                        JOptionPane.showMessageDialog(frame, "Login Successful!", "Login", JOptionPane.INFORMATION_MESSAGE);
                        ServerApp.client.setId(ServerApp.logEmp.getIdNum());
                        ServerApp.client.sendID();
                        frame.setVisible(false);
                        new MainApp(ServerApp.client);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Your Username or password is incorrect! (" + (MAX_ATTEMPTS - totAttempts++) + " Attempts Remaining)", "Login", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

                if (totAttempts == MAX_ATTEMPTS) {
                    System.exit(0);
                }
            }
        });
        //endregion

    }


    public void addComponentsToFrame() {
        account.add(titl, "span, center, grow, wrap");
        account.add(titl4, "span, center, grow, wrap");

        //email frame
        account.add(email, "skip 1, aligny top");
        account.add(emailTxt, "span, wrap, aligny top");

        account.add(id, "skip 1, aligny top");
        account.add(idTXT, "span, wrap, aligny top");

        //buttons
        account.add(login, "span, center, width 100, aligny top");

        frame.add(side, "grow");//cell col row wid h
        frame.add(account, "grow, gapright 10");
    }

    public void setProperties() {
        //frame settings
        frame.pack();
        frame.setSize(frame.getWidth() + 150, frame.getHeight());
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}